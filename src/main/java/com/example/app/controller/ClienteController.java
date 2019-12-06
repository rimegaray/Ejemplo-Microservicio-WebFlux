package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.model.Cliente;
import com.example.app.service.IServiceCliente;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/cliente")
public class ClienteController {

	@Autowired
	private IServiceCliente	service;
	
	@GetMapping
	public Flux<Cliente> findAll(){
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Cliente> findById(@PathVariable String id){
		return service.findById(id);
	}
	
}
