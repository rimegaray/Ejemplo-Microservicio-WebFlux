package com.example.app.service;

import com.example.app.model.Cliente;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IServiceCliente {

	public Flux<Cliente> findAll();
	
	public Mono<Cliente> findById(String id);
	
}
