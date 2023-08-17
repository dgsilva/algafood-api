package com.algaworks.agafood.domain.repository;

import java.util.List;

import com.algaworks.agafood.domain.model.Estado;

public interface EstadoRepository {

	List<Estado> listar();
	Estado buscar(Long id);
	Estado salvar(Estado estado);
	void remover(Long id);
	
}