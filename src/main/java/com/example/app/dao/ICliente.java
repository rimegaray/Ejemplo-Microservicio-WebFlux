package com.example.app.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.app.model.Cliente;

public interface ICliente extends ReactiveMongoRepository<Cliente, String>{

}
