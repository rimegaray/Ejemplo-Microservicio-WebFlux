package com.example.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.ICliente;
import com.example.app.model.Cliente;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ServiceClienteImpl implements IServiceCliente{

	@Autowired
	private ICliente dao;
	
	@Override
	public Flux<Cliente> findAll() {
		return dao.findAll();
	}

	@Override
	public Mono<Cliente> findById(String id) {
		return dao.findById(id);
	}

	@Override
	public Mono<Cliente> create(Cliente cliente) {
		return dao.save(cliente);
	}

	@Override
	public Mono<Cliente> update(Cliente cliente, String id) {
		return dao.findById(id).flatMap(client ->{
			client.setName(cliente.getName());
			return dao.save(client);
		});
	}

	@Override
	public Mono<Void> delete(String id) {
		return dao.findById(id).flatMap(client -> dao.delete(client));
	}
	
	

}
