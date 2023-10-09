package com.algaworks.agafood.domain.repository;


import java.util.List;
import java.util.Optional;


import com.algaworks.agafood.domain.model.Cozinha;

public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {
	
	List<Cozinha> findTodosByNomeContaining(String nome);
	
	Optional<Cozinha>findByNome(String nome);
	
	boolean existsByNome(String nome);

}
