package com.example.UberApi.controller.forms;

import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.UberApi.modelo.Motorista;
import com.example.UberApi.modelo.Veiculo;
import com.example.UberApi.repository.MotoristaRepository;

public class VeiculoForm {

	@NotNull @NotBlank
	private String placa ;
	@NotNull @NotBlank
	private String modelo;
	@NotNull @NotBlank
	private String cor ;
	@NotNull
	private Integer ano ;
	@NotNull 
	private Long motoristaID;

	public Veiculo converter(MotoristaRepository motoristaRepository) {	
		
		Optional<Motorista> motoOp = motoristaRepository.findById(motoristaID);
		Motorista motorista = null;
		if(motoOp.isPresent()) motorista = motoOp.get();
		return new Veiculo(this.placa,this.modelo,this.cor,this.ano, motorista);
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Long getMotoristaID() {
		return motoristaID;
	}

	public void setMotoristaID(Long motoristaID) {
		this.motoristaID = motoristaID;
	}
	

}
