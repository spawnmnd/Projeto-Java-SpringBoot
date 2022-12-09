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

import com.api.agendaContatos.dtos.RoleDto;
import com.api.agendaContatos.models.RoleModel;
import com.api.agendaContatos.services.RoleService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/projeto")
public class RoleController {

	final RoleService roleService;

	public RoleController(RoleService roleService) {		
		this.roleService = roleService;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/role")
	public ResponseEntity<Object> saveRole(@RequestBody @Valid RoleDto roleDto){
		if(roleService.existsByRoleName(roleDto.getRoleName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Rolename is already in use!");
		}
		
		var roleModel = new RoleModel();
		BeanUtils.copyProperties(roleDto, roleModel);		
		return ResponseEntity.status(HttpStatus.CREATED).body(roleService.save(roleModel));
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/role")
	public ResponseEntity<List<RoleModel>> getAllRoles() {
		return ResponseEntity.status(HttpStatus.OK).body(roleService.findAll());
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/role/{id}")
	public ResponseEntity<Object> getOneRole(@PathVariable(value = "id") UUID id) {
		Optional<RoleModel> roleModelOptional = roleService.findById(id);
		if (!roleModelOptional.isPresent()) {// verifica se esta presente
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rolename not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(roleModelOptional.get());
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/role/{id}")
	public ResponseEntity<Object> deleteRole(@PathVariable(value = "id") UUID id) {
		Optional<RoleModel> roleModelOptional = roleService.findById(id);
		if (!roleModelOptional.isPresent()) {// verifica se esta presente
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rolename not found.");
		}
		roleService.delete(roleModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Rolename deleted successfully.");
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/role/{id}")
	public ResponseEntity<Object> updateRole(@PathVariable(value = "id") UUID id,
			@RequestBody @Valid RoleDto roleDto) {
		Optional<RoleModel> roleModelOptional = roleService.findById(id);
		if (!roleModelOptional.isPresent()) {// verifica se existe registro para atualizar
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rolename not found.");
		}
		var roleModel = new RoleModel();
		BeanUtils.copyProperties(roleDto, roleModel);
		roleModel.setRoleId(roleModelOptional.get().getRoleId());		
		return ResponseEntity.status(HttpStatus.OK).body(roleService.save(roleModel));
	}
	
}
