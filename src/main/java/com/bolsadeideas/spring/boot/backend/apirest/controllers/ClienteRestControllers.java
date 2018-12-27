package com.bolsadeideas.spring.boot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.spring.boot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.spring.boot.backend.apirest.models.services.IClienteService;

@CrossOrigin(origins= {"http://localhost:4200"}) // Reglas que permiten a nuestro front conectarse con back
@RestController
@RequestMapping("/api")
public class ClienteRestControllers {
	
	@Autowired //Inyectamos el clineteService
	private IClienteService  clienteService;
	
	@GetMapping("/clientes")
	public List<Cliente> index(){
		return clienteService.findAll();		
	}
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		Cliente cliente = null;
		
		//MANEJANDO EL ERROR QUE PUEDA OCURRIR EN EL SERVIODR
		try {
			cliente = clienteService.findById(id);
		} catch(DataAccessException e) {
			response.put("Status", "ERROR");
			response.put("Message", "Error al ejecutar ");
			response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Validando de que el id Exista
		if( cliente == null) {
			response.put("Status", "ERROR");
			response.put("Message", "El cliente Con el ID: " .concat(id.toString().concat(" no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	@GetMapping("/cliente/{name}")
	public Cliente showCliente(@PathVariable String name) {
		return clienteService.findByName(name);
	}
	
	//Clientes Nuevos
	@PostMapping("/clientes")
	//@ResponseStatus(HttpStatus.CREATED) //Estatus 201 cuando se creo algo exitoso
	public ResponseEntity<?> create(@RequestBody Cliente cliente) {
		Cliente clientenew= null;
		Map<String, Object> response = new HashMap<>();
		try {
			 clientenew =clienteService.save(cliente);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("Status", "ERROR");
			response.put("Message", "Error al ejecutar ");
			response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Status", "OK");
		response.put("Message", "Cliente Creado Exitosamente!!");
		response.put("Cliente", clientenew);
		return new ResponseEntity<Map<String, Object>>( response, HttpStatus.CREATED );
	}
	
	@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED) 
	public ResponseEntity<?> update(@RequestBody Cliente cliente ,@PathVariable Long id) {
		Cliente clienteActual = clienteService.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		//Validando de que el id Exista
		if( clienteActual == null) {
			response.put("Status", "ERROR");
			response.put("Message", "El Cliente Con el ID: " .concat(id.toString().concat(" no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		Cliente updatecliente = null;
		
		try {
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setEmail(cliente.getEmail());
		clienteActual.setCreateAt(cliente.getCreateAt());
		
		updatecliente = clienteService.save(clienteActual);
		
		}catch(DataAccessException e){
			response.put("Status", "ERROR");
			response.put("Message", "No se pudo Actualizar el Cliente");
			response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		response.put("Status", "OK");
		response.put("Message", "Cliente Actualizado Exitosamente!!");
		response.put("Cliente", updatecliente);
		return new ResponseEntity<Map<String, Object>>( response, HttpStatus.CREATED );
		
	}
	
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			clienteService.delete(id);
		}catch(DataAccessException e) {
			response.put("Status", "ERROR");
			response.put("Message", "No se pudo Eliminar el Cliente");
			response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Status", "OK");
		response.put("Message", "Cliente Eliminado Exitosamente!!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
}
