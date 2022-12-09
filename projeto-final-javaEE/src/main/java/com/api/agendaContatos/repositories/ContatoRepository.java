package com.api.agendaContatos.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.agendaContatos.models.ContatoModel;

public interface ContatoRepository extends JpaRepository<ContatoModel, UUID>{
	
	boolean existsByNum(String num);
	
}
