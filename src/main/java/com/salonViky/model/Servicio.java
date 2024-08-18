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
@Table(name="servicios")
public class Servicio {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="servicio_id")
	private Integer id;
	
	
	
	@NotNull @NotBlank @NotEmpty
	private String nombre;
	
	@NotNull @NotBlank @NotEmpty
	private String descripcion;
	
	@NotNull
	private Double precio;
	
	@NotNull @NotBlank @NotEmpty
	private String estado;
	
	@OneToMany(mappedBy = "servicio")
	private List<VentaDetalle> detalles;
	
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	public Servicio() {
		super();
	}
	public Servicio(Integer id) {
		super();
		this.id = id;
	}
	public Servicio(Integer id, String nombre, String descripcion, Double precio, String estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "Servicio [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio
				+ ", estado=" + estado + "]";
	}
	
	
	
}
