package com.bolsadeideas.spring.boot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.spring.boot.backend.apirest.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{
	
	//query personalizado
	@Query(value = "select * from clientes where nombre like %?1%", nativeQuery = true)
	public List<Cliente> findByName(String item);
}
