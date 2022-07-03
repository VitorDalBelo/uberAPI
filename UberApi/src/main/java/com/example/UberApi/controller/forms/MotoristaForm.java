package com.example.UberApi.controller.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.UberApi.modelo.Cliente;
import com.example.UberApi.modelo.Motorista;

public class MotoristaForm {
	@NotNull @NotBlank
	private String nome;
	@NotNull @NotBlank
	private String fone;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}
	public Motorista converter() {
		// TODO Auto-generated method stub
		return new Motorista(this.fone,this.nome);
	}
}
