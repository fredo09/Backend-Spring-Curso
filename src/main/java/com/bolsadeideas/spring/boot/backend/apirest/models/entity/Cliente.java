package com.bolsadeideas.spring.boot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="clientes")
public class Cliente implements Serializable{
	
	@Id //Asignando el ID
	@GeneratedValue(strategy=GenerationType.IDENTITY) //incremento del id
	private Long id;
	private String nombre;
	private String apellido;
	private String email;
	
	//Cambiando el nombre de dato de la columna 
	@Column(name="create_at")
	@Temporal(TemporalType.DATE) // Cambiando el formato de fecha de java a mysql
	private Date CreateAt;
	
	@PrePersist // pre presistimos los datos antes del save
	public void prePersis() {
		CreateAt = new Date();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateAt() {
		return CreateAt;
	}
	public void setCreateAt(Date createAt) {
		CreateAt = createAt;
	}
	
	private static final long serialVersionUID = 1L;

}