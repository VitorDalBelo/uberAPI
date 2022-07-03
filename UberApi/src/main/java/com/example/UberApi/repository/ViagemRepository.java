package com.example.UberApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UberApi.modelo.Viagem;

public interface ViagemRepository extends JpaRepository<Viagem,Long> {

}
