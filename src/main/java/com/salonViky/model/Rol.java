package com.salonViky.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // el identity = usa la pk generada en el postgress
	@Column(name = "rol_id") // le digo que en mi bd se llama rol_id
	private Integer id;

	@NotBlank
	private String descripcion;

	@OneToMany(mappedBy = "rol")
	private List<Usuario> usuarios;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Rol(Integer id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public Rol(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Rol [id=" + id + ", descripcion=" + descripcion + "]";
	}

	public Rol() {
		super();
	}

}
