package com.api.agendaContatos.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.api.agendaContatos.models.ContatoModel;
import com.api.agendaContatos.repositories.ContatoRepository;



@Service
public class ContatoService {

	final ContatoRepository contatoRepository;

	public ContatoService(ContatoRepository contatoRepository) {		
		this.contatoRepository = contatoRepository;
	}
	
	@Transactional
	public ContatoModel save(ContatoModel contatoModel) {
		return contatoRepository.save(contatoModel);
	}
	
	public boolean existsByNum(String num) {
		return contatoRepository.existsByNum(num);
	}
	
	public List<ContatoModel> findAll(){
		return contatoRepository.findAll();
	}
	
	public Optional<ContatoModel> findById(UUID id){
		return contatoRepository.findById(id);
	}
	
	@Transactional
	public void delete(ContatoModel contatoModel) {
		contatoRepository.delete(contatoModel);
	}
	
}
