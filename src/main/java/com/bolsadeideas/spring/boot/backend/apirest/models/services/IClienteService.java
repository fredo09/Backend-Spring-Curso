package com.bolsadeideas.spring.boot.backend.apirest.models.services;

import java.util.List;

import com.bolsadeideas.spring.boot.backend.apirest.models.entity.Cliente;

public interface IClienteService {
	
	//Listando los clientes
	public List<Cliente> findAll();
	
	public Cliente findById(Long id);
	
	public Cliente save(Cliente cliente);
	
	public void delete(Long id);
	
	public Cliente findByName(String item);
}
