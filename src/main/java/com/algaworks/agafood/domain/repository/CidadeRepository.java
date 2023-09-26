package com.algaworks.agafood.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.algaworks.agafood.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

	
}