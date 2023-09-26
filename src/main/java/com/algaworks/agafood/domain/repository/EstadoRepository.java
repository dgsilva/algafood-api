package com.algaworks.agafood.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.agafood.domain.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
	
}