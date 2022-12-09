package com.api.agendaContatos.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.agendaContatos.dtos.UserDto;
import com.api.agendaContatos.models.UserModel;
import com.api.agendaContatos.services.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/projeto")
public class UserController {
	
	final UserService userService;

	public UserController(UserService userService) {		
		this.userService = userService;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/user")
	public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto){
		
		if(userService.existsByUsername(userDto.getUsername())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Username is already in use!");
		}
		
		var userModel = new UserModel();
		BeanUtils.copyProperties(userDto, userModel);		
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/user")
	public ResponseEntity<List<UserModel>> getAllUsers() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/user/{id}")
	public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") UUID id) {
		Optional<UserModel> userModelOptional = userService.findById(id);
		if (!userModelOptional.isPresent()) {// verifica se esta presente
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/user/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id) {
		Optional<UserModel> userModelOptional = userService.findById(id);
		if (!userModelOptional.isPresent()) {// verifica se esta presente
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
		userService.delete(userModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/user/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id,
			@RequestBody @Valid UserDto userDto) {
		Optional<UserModel> userModelOptional = userService.findById(id);
		if (!userModelOptional.isPresent()) {// verifica se existe registro para atualizar
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
		var userModel = new UserModel();
		BeanUtils.copyProperties(userDto, userModel);
		userModel.setUserId(userModelOptional.get().getUserId());		
		return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
	}
	
}
