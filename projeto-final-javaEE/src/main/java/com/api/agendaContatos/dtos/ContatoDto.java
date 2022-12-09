package com.api.agendaContatos.dtos;

import javax.validation.constraints.NotBlank;

public class ContatoDto {
	
	@NotBlank
	private String nome;
	@NotBlank
	private String num;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}	
	
}
