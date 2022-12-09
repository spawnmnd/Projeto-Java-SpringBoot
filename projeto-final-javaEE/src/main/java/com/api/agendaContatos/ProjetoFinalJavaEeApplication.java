package com.api.agendaContatos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Marcelo
 *
 */

@SpringBootApplication
@RestController
public class ProjetoFinalJavaEeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoFinalJavaEeApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("senha123"));
	}
	
	@GetMapping("/")
	public String index() {
		return "Projeto rodando!";
	}

}
