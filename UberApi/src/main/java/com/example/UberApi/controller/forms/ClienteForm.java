package com.example.UberApi.controller.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.UberApi.modelo.Cliente;

public class ClienteForm {
	@NotNull @NotBlank
	private String endereco;
	@NotNull @NotBlank
	private String fone;
	@NotNull @NotBlank
	private String nome ;
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Cliente converter() {
		// TODO Auto-generated method stub
		return new Cliente(this.endereco,this.fone,this.nome);
	}
	
	
	
	
}
