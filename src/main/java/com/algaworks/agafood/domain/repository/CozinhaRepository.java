package com.algaworks.agafood.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.agafood.domain.model.Cozinha;

public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {
	
//	List<Cozinha> consultarPorNome(String nome);

}
