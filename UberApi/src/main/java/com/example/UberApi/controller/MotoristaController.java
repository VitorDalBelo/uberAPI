package com.example.UberApi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.UberApi.controller.forms.ClienteForm;
import com.example.UberApi.controller.forms.MotoristaForm;
import com.example.UberApi.modelo.Cliente;
import com.example.UberApi.modelo.Motorista;
import com.example.UberApi.repository.MotoristaRepository;

@RestController
@RequestMapping("motoristas")
public class MotoristaController {
	@Autowired
	private MotoristaRepository motoristaRepository;
	
	@GetMapping
	public List<Motorista> readMotoristas(Long id,String nome,String fone){
		Motorista motorista = new Motorista();
		if(nome!=null) motorista.setNome(nome);
		if(fone!=null) motorista.setFone(fone);
		if(id!=null) motorista.setId(id);
		Example<Motorista> motoristaEx = Example.of(motorista);
		return motoristaRepository.findAll(motoristaEx);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Motorista> readMotorista(@PathVariable Long id) {
		Optional<Motorista> optional = motoristaRepository.findById(id);
		if(optional.isPresent())return ResponseEntity.ok(optional.get());
		else return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Motorista> createCliente(@RequestBody @Valid MotoristaForm motoristaForm,UriComponentsBuilder uriBuilder) {
		Motorista motorista = motoristaRepository.save(motoristaForm.converter());
		URI uri = uriBuilder.path("clientes/{id}").buildAndExpand(motorista.getId()).toUri();
		return ResponseEntity.created(uri).body(motorista);
	}	
	
	@DeleteMapping("/{id}")
	@Transactional 
	public ResponseEntity<?> deleteCliente(@PathVariable Long id){
		Optional<Motorista> motoristaOptional = motoristaRepository.findById(id);
		if(motoristaOptional.isPresent()) {
			motoristaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		else return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Motorista> updateAllCliente(@RequestBody @Valid MotoristaForm motoristaForm,@PathVariable Long id) {
		Optional<Motorista> motoristaOptional = motoristaRepository.findById(id);
		if(motoristaOptional.isPresent()) {
			Motorista motorista = motoristaOptional.get();
			motorista.setFone(motoristaForm.getFone());
			motorista.setNome(motoristaForm.getNome());
			return ResponseEntity.ok(motorista);
		}
		else return ResponseEntity.notFound().build();	
	}
	

	
	
	
}
