package com.api.agendaContatos.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

import com.api.agendaContatos.dtos.ContatoDto;
import com.api.agendaContatos.models.ContatoModel;
import com.api.agendaContatos.services.ContatoService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/projeto") // http://localhost:8085/projeto
public class ContatoController {
	
	final ContatoService contatoService;

	public ContatoController(ContatoService contatoService) {
		this.contatoService = contatoService;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/contato") // http://localhost:8085/projeto/contato
	public ResponseEntity<Object> saveContato(@RequestBody @Valid ContatoDto contatoDto) {		
		if (contatoService.existsByNum(contatoDto.getNum())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Num is already in use!");
		}
		
		var contatoModel = new ContatoModel();
		BeanUtils.copyProperties(contatoDto, contatoModel);
		contatoModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		return ResponseEntity.status(HttpStatus.CREATED).body(contatoService.save(contatoModel));
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/contato")
	public ResponseEntity<List<ContatoModel>> getAllContatos() {
		return ResponseEntity.status(HttpStatus.OK).body(contatoService.findAll());
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/contato/{id}")
	public ResponseEntity<Object> getOneContato(@PathVariable(value = "id") UUID id) {
		Optional<ContatoModel> contatoModelOptional = contatoService.findById(id);
		if (!contatoModelOptional.isPresent()) {// verifica se esta presente
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contato not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(contatoModelOptional.get());
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/contato/{id}")
	public ResponseEntity<Object> deleteContato(@PathVariable(value = "id") UUID id) {
		Optional<ContatoModel> contatoModelOptional = contatoService.findById(id);
		if (!contatoModelOptional.isPresent()) {// verifica se esta presente
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contato not found.");
		}
		contatoService.delete(contatoModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Contato deleted successfully.");
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/contato/{id}")
	public ResponseEntity<Object> updateContato(@PathVariable(value = "id") UUID id,
			@RequestBody @Valid ContatoDto contatoDto) {
		Optional<ContatoModel> contatoModelOptional = contatoService.findById(id);
		if (!contatoModelOptional.isPresent()) {// verifica se existe registro para atualizar
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contato not found.");
		}
		var contatoModel = new ContatoModel();
		BeanUtils.copyProperties(contatoDto, contatoModel);
		contatoModel.setId(contatoModelOptional.get().getId());
		contatoModel.setRegistrationDate(contatoModelOptional.get().getRegistrationDate());
		return ResponseEntity.status(HttpStatus.OK).body(contatoService.save(contatoModel));
	}
	
}
