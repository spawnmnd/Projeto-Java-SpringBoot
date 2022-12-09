package com.api.agendaContatos.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.agendaContatos.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, UUID>{
	
	Optional<UserModel> findByUsername(String username);
	
	boolean existsByUsername(String username);
	
}
