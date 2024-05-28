package com.algaworks.agafood.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.agafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.agafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.agafood.domain.model.Cozinha;
import com.algaworks.agafood.domain.model.Restaurante;
import com.algaworks.agafood.domain.repository.CozinhaRepository;
import com.algaworks.agafood.domain.repository.RestauranteRepository;


@Service
public class CadastroRestauranteService {

	@Autowired
	CozinhaRepository cozinhaRepository;
	@Autowired
	RestauranteRepository restauranteRepository;
	
	public Restaurante salvar(@RequestBody Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format("Não existe cadastro de cozinha com código %d", cozinhaId)));
		
		restaurante.setCozinha(cozinha);
		
		
		return restauranteRepository.save(restaurante);
	}
	
	
	public void excluir(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro de restaurante com código %d", restauranteId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format("Restaurante de código %d não pode ser removida, pois está em uso", restauranteId));
		}
	}
	
	
}
