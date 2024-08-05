package com.algaworks.agafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public  class EntidadeNaoEncontradaException extends NegocioException {

	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public EntidadeNaoEncontradaException(Long cidadeId) {
		this(String.format("Não existe um cadastro de cidade com código %d", cidadeId));
	}
}
