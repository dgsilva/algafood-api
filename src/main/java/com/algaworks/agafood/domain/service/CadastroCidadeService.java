package com.algaworks.agafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.agafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.agafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.agafood.domain.model.Cidade;
import com.algaworks.agafood.domain.model.Estado;
import com.algaworks.agafood.domain.repository.CidadeRepository;
import com.algaworks.agafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {
	
	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe um cadastro de cidade com código %d";

	@Autowired
	CidadeRepository cidadeRepository;
	
	@Autowired
	EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		
		Estado estado = cadastroEstadoService.buscarOuFalhar(estadoId);
//		Estado estado = estadoRepository.findById(estadoId)
//		.orElseThrow(() -> new EntidadeNaoEncontradaException(
//				String.format("Não existe cadastro de estado com código %d", estadoId)));
		cidade.setEstado(estado);
				
		return cidadeRepository.save(cidade);
	}
	
	
	public void excluir(Long restauranteId) {
		try {
			cidadeRepository.deleteById(restauranteId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
				String.format(MSG_CIDADE_NAO_ENCONTRADA, restauranteId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_CIDADE_EM_USO, restauranteId));
		}
	}
	
	public Cidade buscarOuFalhar(Long restauranteId) {
		return cidadeRepository.findById(restauranteId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, restauranteId)));
	}

}
