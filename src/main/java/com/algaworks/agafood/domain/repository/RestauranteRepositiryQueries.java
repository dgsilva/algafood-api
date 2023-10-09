package com.algaworks.agafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.agafood.domain.model.Restaurante;

public interface RestauranteRepositiryQueries {

	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	List<Restaurante> findComFreteGratis(String nome);
}