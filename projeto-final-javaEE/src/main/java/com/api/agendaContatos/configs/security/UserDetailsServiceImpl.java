package com.api.agendaContatos.configs.security;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.agendaContatos.models.UserModel;
import com.api.agendaContatos.repositories.UserRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{
	
final UserRepository userRepository;
	
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel userModel = userRepository.findByUsername(username)
	.orElseThrow(()-> new UsernameNotFoundException("User Not Found with username "  + username));
		return new User(userModel.getUsername(), userModel.getPassword(), true, true, true, true,
				userModel.getAuthorities());
	}	
	
}
