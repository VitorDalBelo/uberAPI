package com.example.UberApi.controller;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.UberApi.controller.forms.ClienteForm;
import com.example.UberApi.modelo.Cliente;
import com.example.UberApi.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	@Autowired
	private ClienteRepository clienteRepository;
	@GetMapping
	public List<Cliente> readClientes(Long id,String endereco,String nome,String fone){
		Cliente cliente = new Cliente();
		if(endereco!=null) cliente.setEndereco(endereco);
		if(nome!=null) cliente.setNome(nome);
		if(fone!=null) cliente.setFone(fone);
		if(id!=null) cliente.setId(id);
		
		
		Example<Cliente> c = Example.of(cliente);
		
	
		
		// clienteRepository.findAll(c);
		
		return clienteRepository.findAll(c);

	}
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> readCliente(@PathVariable Long id) {
		Optional<Cliente> optional = clienteRepository.findById(id);
		if(optional.isPresent())return ResponseEntity.ok(optional.get());
		else return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Cliente> createCliente(@RequestBody @Valid ClienteForm clienteForm,UriComponentsBuilder uriBuilder) {
		Cliente cliente = clienteRepository.save(clienteForm.converter());
		URI uri = uriBuilder.path("clientes/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).body(cliente);
	}


	@PatchMapping("/{id}")
	@Transactional
	public ResponseEntity<Cliente> updatePartillyCliente(@RequestBody Map<String,Object> clienteForm,@PathVariable Long id ) {
		Optional<Cliente> clienteOptional = clienteRepository.findById(id);
		
		if(clienteOptional.isPresent()) {
			Cliente cliente = clienteOptional.get();	
			
			clienteForm.forEach((key , value)->{
				Field field = ReflectionUtils.findField(Cliente.class, key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, cliente, value);
			});
			return ResponseEntity.ok(cliente);
		}
		else return ResponseEntity.notFound().build();
		
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Cliente> updateAllCliente(@RequestBody @Valid ClienteForm clienteForm,@PathVariable Long id) {
		Optional<Cliente> clienteOptional = clienteRepository.findById(id);
		if(clienteOptional.isPresent()) {
			Cliente cliente = clienteOptional.get();
			cliente.setEndereco(clienteForm.getEndereco());
			cliente.setFone(clienteForm.getFone());
			cliente.setNome(clienteForm.getNome());
			return ResponseEntity.ok(cliente);
		}
		else return ResponseEntity.notFound().build();	
	}
	@DeleteMapping("/{id}")
	@Transactional 
	public ResponseEntity<?> deleteCliente(@PathVariable Long id){
		Optional<Cliente> clienteOptional = clienteRepository.findById(id);
		if(clienteOptional.isPresent()) {
			clienteRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		else return ResponseEntity.notFound().build();
	
	}
	

}
