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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.UberApi.controller.forms.ClienteForm;
import com.example.UberApi.controller.forms.VeiculoForm;
import com.example.UberApi.erroHandle.ErroDependenciaNaoEncontrada;
import com.example.UberApi.modelo.Cliente;
import com.example.UberApi.modelo.Motorista;
import com.example.UberApi.modelo.Veiculo;
import com.example.UberApi.repository.MotoristaRepository;
import com.example.UberApi.repository.VeiculoRepository;

import veiculosDTOS.VeiculoDto;


@RestController
@RequestMapping("/veiculos")
public class VeiculoController {
	@Autowired
	private VeiculoRepository veiculoRepository;
	@Autowired
	private MotoristaRepository motoristaRepository;

	@GetMapping
	@Transactional
	public List<VeiculoDto> readClientes(Long id , String placa , String modelo , String cor,Integer ano){
		Veiculo v = new Veiculo();
		if(id!=null) v.setId(id);
		if(placa!=null)v.setPlaca(placa);
		if(modelo!=null)v.setModelo(modelo);
		if(cor!=null)v.setCor(cor);
		if(ano!=null)v.setAno(ano);
		
		Example<Veiculo> c = Example.of(v);
		
		List<Veiculo> veiculos = veiculoRepository.findAll(c);
		
		return VeiculoDto.converter(veiculos);

	}
	@GetMapping("/{id}")
	@Transactional 
	public ResponseEntity<VeiculoDto> detelhar(@PathVariable Long id){
		Optional<Veiculo> veiculo = veiculoRepository.findById(id);
		if(veiculo.isPresent()) return ResponseEntity.ok(new VeiculoDto(veiculo.get()) );
		else return ResponseEntity.notFound().build();
	}
	@PostMapping
	@Transactional 
	public ResponseEntity<VeiculoDto> createCliente(@RequestBody @Valid VeiculoForm veiculoForm,UriComponentsBuilder uriBuilder) throws ErroDependenciaNaoEncontrada {
		Veiculo veiculo = veiculoForm.converter(motoristaRepository);
		if(veiculo.getMotorista()==null) {
			FieldError f = new FieldError("Veiculo","motorista","id do motorista não corresponde com nenhum motorista cadastrado");
			ErroDependenciaNaoEncontrada err = new ErroDependenciaNaoEncontrada(f);
			throw err;
		}
		veiculoRepository.save(veiculo);
		URI uri = uriBuilder.path("veiculos/{id}").buildAndExpand(veiculo.getId()).toUri();
		return ResponseEntity.created(uri).body(new VeiculoDto(veiculo));
	}
	@PatchMapping("/{id}")
	@Transactional
	public ResponseEntity<VeiculoDto> updatePartillyCliente(@RequestBody Map<String,Object> veiculoRequest,@PathVariable Long id ) throws ErroDependenciaNaoEncontrada{
		Optional<Veiculo> veiculoOptional = veiculoRepository.findById(id);
		
		if(veiculoOptional.isPresent()) {
			Veiculo veiculo = veiculoOptional.get();
			
			if(veiculoRequest.get("motorista")!=null) {
				Optional<Motorista> motoOp = motoristaRepository.findById(Long.valueOf(String.valueOf(veiculoRequest.get("motorista"))));
				if(motoOp.isPresent()) veiculoRequest.put("motorista", motoOp.get());
				else {
					FieldError f = new FieldError("Veiculo","motorista","id do motorista não corresponde com nenhum motorista cadastrado");
					ErroDependenciaNaoEncontrada err = new ErroDependenciaNaoEncontrada(f);
					throw err;
				}
			}
			
			veiculoRequest.forEach((key , value)->{
				Field field = ReflectionUtils.findField(Veiculo.class, key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, veiculo, value);
			});

			VeiculoDto dto = new VeiculoDto(veiculo);
			return ResponseEntity.ok(dto);
		}
		else return ResponseEntity.notFound().build();
		
	}
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<VeiculoDto> updateAllCliente(@RequestBody @Valid VeiculoForm veiculoForm,@PathVariable Long id) throws ErroDependenciaNaoEncontrada {
		Optional<Veiculo> veiculoOptional = veiculoRepository.findById(id);
		Veiculo novoVeiculo = veiculoForm.converter(motoristaRepository);
		if(veiculoOptional.isPresent()) {
			if(novoVeiculo.getMotorista()==null) {
				FieldError f = new FieldError("Veiculo","motorista","id do motorista não corresponde com nenhum motorista cadastrado");
				ErroDependenciaNaoEncontrada err = new ErroDependenciaNaoEncontrada(f);
				throw err;
			}
			Veiculo veiculo = veiculoOptional.get();
			veiculo.setAno(novoVeiculo.getAno());
			veiculo.setCor(novoVeiculo.getCor());
			veiculo.setPlaca(novoVeiculo.getPlaca());
			veiculo.setModelo(novoVeiculo.getModelo());
			veiculo.setMotorista(novoVeiculo.getMotorista());
			VeiculoDto dto = new VeiculoDto(veiculo);
			return ResponseEntity.ok(dto);
		}
		else return ResponseEntity.notFound().build();	
	}
	@DeleteMapping("/{id}")
	@Transactional 
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		
		Optional<Veiculo> optional = veiculoRepository.findById(id);
		if(optional.isPresent()) {
			veiculoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		else return ResponseEntity.notFound().build();

	}
	

}
