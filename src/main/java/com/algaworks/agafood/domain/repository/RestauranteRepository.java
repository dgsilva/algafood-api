package com.algaworks.agafood.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.agafood.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
}
