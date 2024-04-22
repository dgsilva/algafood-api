package com.algaworks.agafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.agafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.agafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.agafood.domain.model.Restaurante;
import com.algaworks.agafood.domain.repository.RestauranteRepository;
import com.algaworks.agafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@GetMapping
	public List<Restaurante> listar() {
		List<Restaurante> restaurantes = restauranteRepository.findAll();
		System.out.println("O nome da cozinha é: ");
		System.out.println(restaurantes.get(0).getNome());
		return restaurantes;
	}

	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);

		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping()
	public Restaurante adicionar(@RequestBody Restaurante restaurante) {
		return cadastroRestaurante.salvar(restaurante);
	}
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,@RequestBody Restaurante restaurante) {
		try {
		Restaurante restauranteAtual = restauranteRepository
				.findById(restauranteId).orElse(null);
		
		if (restauranteAtual != null) {
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco","dataCadastro","produtos");
			Restaurante restauranteSalva = restauranteRepository.save(restauranteAtual);
			return ResponseEntity.ok(restauranteSalva);
		}
		
		return ResponseEntity.notFound().build();
		
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId) {
		try {
			cadastroRestaurante.excluir(restauranteId);	
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?>atualizarParcial(@PathVariable Long restauranteId,@RequestBody Map<String,Object>campos){
		Restaurante restauranteAtual = restauranteRepository
				.findById(restauranteId).orElse(null);
		
		if(restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}
		
		merge(campos,restauranteAtual);
		
		return atualizar(restauranteId, restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem,Restaurante restauranteAtual) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
		
		dadosOrigem.forEach((nomePropriedade, valorPropriedade)->{
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
			ReflectionUtils.setField(field, restauranteAtual, novoValor);
		});
	}
	
	

}
