package com.algaworks.agafood.api.controller;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.agafood.domain.model.Cozinha;
import com.algaworks.agafood.domain.model.Restaurante;
import com.algaworks.agafood.domain.repository.CozinhaRepository;
import com.algaworks.agafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("teste")
public class TesteController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha>cozinhasPorNome(@RequestParam String nome){
		return cozinhaRepository.findTodosByNomeContaining(nome);
	}
	
	@GetMapping("/cozinhas/unica-por-nome")
	public Optional<Cozinha>cozinhasNome(@RequestParam String nome){
		return cozinhaRepository.findByNome(nome);
	}
	
	
	@GetMapping("/cozinhas/exists")
	public boolean cozinhaExists(String nome){
		return cozinhaRepository.existsByNome(nome);
	}
	
	@GetMapping("/cozinhas/primeiro")
	public Optional<Cozinha> cozinhaPrimeiro() {
		return cozinhaRepository.buscarPrimeiro();
	}
	
	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante>restaurantePorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.queryByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes/por-nome-cozinha")
	public List<Restaurante>restaurantePorNomeAndCozinha(String nome, Long cozinhaId){
		return restauranteRepository.consultarPorNome(nome, cozinhaId);
	}
	
	@GetMapping("/restaurantes/primeiro-nome-cozinha")
	public Optional<Restaurante>restaurantePorNomeCozinha(String nome){
		return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
	}
	
	@GetMapping("/restaurantes/top2-por-nome")
	public List<Restaurante>restauranteTopPorNome(String nome){
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}
	
	
	@GetMapping("/restaurantes/por-nome-e-frete")
	public List<Restaurante> restaurantesPorNomeFrete(String nome, 
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
	}

	
	
	@GetMapping("/restaurantes/count-por-cozinha")
	public int restaurantesCountPorCozinha(Long cozinhaId){
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}
	
	
	@GetMapping("/restaurantes/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome) {
		return restauranteRepository.findComFreteGratis(nome);
	}
	
	@GetMapping("/restaurantes/primeiro")
	public Optional<Restaurante> restaurantePrimeiro() {
		return restauranteRepository.buscarPrimeiro();
	}
	
	
}
