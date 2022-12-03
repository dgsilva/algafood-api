package com.algaworks.algafood.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.modelo.Cliente;
import com.algaworks.algafood.notificacao.Notificador;

@Component
public class AtivacaoClienteService {

	@Autowired
	private List<Notificador> notificador;
	
	public void ativar(Cliente cliente) {
		cliente.ativar();
		for (Notificador notificador : notificador) {
			notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
		}
	}
}
