package com.salonViky.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="usuario_id")
	private Integer id;
	
	@NotNull @NotBlank @NotEmpty
	private String nombre;
	
	@NotNull @NotBlank @NotEmpty
	private String clave;
	
	@NotNull @NotBlank @NotEmpty
	private String email;
	
	@NotNull @NotBlank @NotEmpty
	private String estado;
	
	@ManyToOne
	@JoinColumn(name="rol_id")
	private Rol rol;
	
	
	
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getClave() {
		return clave;
	}



	public void setClave(String clave) {
		this.clave = clave;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public Rol getRol() {
		return rol;
	}



	public void setRol(Rol rol) {
		this.rol = rol;
	}



	public Usuario() {
		super();
	}



	public Usuario(Integer id, String nombre, String clave, String email, String estado, Rol rol) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.clave = clave;
		this.email = email;
		this.estado = estado;
		this.rol = rol;
	}



	public Usuario(Integer id) {
		super();
		this.id = id;
	}



	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", clave=" + clave + ", email=" + email + ", estado="
				+ estado + ", rol=" + rol + "]";
	}
	
	
	
	

}
