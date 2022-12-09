package com.api.agendaContatos.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.agendaContatos.enums.RoleName;
import com.api.agendaContatos.models.RoleModel;

public interface RoleRepository extends JpaRepository<RoleModel, UUID>{
	
	@SuppressWarnings("unchecked")
	RoleModel save(RoleModel roleModel);

	boolean existsByRoleName(RoleName roleName);

}
