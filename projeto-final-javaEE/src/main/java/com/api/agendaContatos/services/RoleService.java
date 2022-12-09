package com.api.agendaContatos.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.api.agendaContatos.enums.RoleName;
import com.api.agendaContatos.models.RoleModel;
import com.api.agendaContatos.repositories.RoleRepository;

@Service
public class RoleService {
	
	final RoleRepository roleRepository;

	public RoleService(RoleRepository roleRepository) {		
		this.roleRepository = roleRepository;
	}
	
	@Transactional
	public RoleModel save(RoleModel roleModel) {
		
		return roleRepository.save(roleModel);
		
	}
	
	public boolean existsByRoleName(RoleName roleName) {
		
		return roleRepository.existsByRoleName(roleName);
		
	}
	
	public List<RoleModel> findAll() {

		return roleRepository.findAll();
	}
	
	public Optional<RoleModel> findById(UUID id) {

		return roleRepository.findById(id);
	}
	
	@Transactional
	public void delete(RoleModel roleModel) {

		roleRepository.delete(roleModel);

	}
	
}
