package com.example.app.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.example.app.model.Cliente;
import com.example.app.service.IServiceCliente;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/cliente")
public class ClienteController {

	@Autowired
	private IServiceCliente	service;
	
	@GetMapping("/listar")	
	public Flux<Cliente> findAll(){
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Cliente> findById(@PathVariable String id){
		return service.findById(id);
	}
	
	@PostMapping
	public Mono<Map<String,Object>> create(@Valid @RequestBody Mono<Cliente> monoCliente){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		return monoCliente.flatMap(cliente->{
			respuesta.put("cliente", cliente);
			respuesta.put("mensaje", "Cliente creado con exito");
			return service.create(cliente).map(x -> respuesta);
		}).onErrorResume(t->{
			return Mono.just(t).cast(WebExchangeBindException.class)
					.flatMap(e -> Mono.just(e.getFieldErrors()))
					.flatMapMany(Flux::fromIterable)
					.map(fieldError -> "el campo "+fieldError.getField()+" "+fieldError.getDefaultMessage())
					.collectList()
					.flatMap(list->{
						respuesta.put("errors", list);
						respuesta.put("status", HttpStatus.BAD_REQUEST.value());
						return Mono.just(respuesta);
					});
		});
		
	}
	
	@PutMapping("/{id}")
	public Mono<Cliente> update(@RequestBody Cliente cliente, @PathVariable String id){
		return service.update(cliente, id);
	}
	
	@DeleteMapping("/{id}")
	public Mono<Void> delete(@PathVariable String id){
		return service.delete(id);
	}
}
