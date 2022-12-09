package com.api.agendaContatos.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.api.agendaContatos.models.UserModel;
import com.api.agendaContatos.repositories.UserRepository;

@Service
public class UserService {
	
	final UserRepository userRepository;

	public UserService(UserRepository userRepository) {		
		this.userRepository = userRepository;
	}
	
	@Transactional
	public UserModel save(UserModel userModel) {
		
		return userRepository.save(userModel);
		
	}
	
	public boolean existsByUsername(String username) {
		
		return userRepository.existsByUsername(username);
				
	}
	
	public List<UserModel> findAll() {

		return userRepository.findAll();
	}
	
	public Optional<UserModel> findById(UUID id) {

		return userRepository.findById(id);
	}
	
	@Transactional
	public void delete(UserModel userModel) {

		userRepository.delete(userModel);

	}
	
}
