package com.example.UberApi.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UberApi.modelo.Cliente;



public interface ClienteRepository extends JpaRepository<Cliente,Long> {



	List<Cliente> findByNomeLikeAndEnderecoLikeAndFoneEqualsAndIdEquals(Long id, String endereco, String nome,
			String fone);
	
}
