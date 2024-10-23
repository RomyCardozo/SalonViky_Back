package com.salonViky.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "auditorias")
public class Auditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // el identity = usa la pk generada en el postgress
	private Integer id;
	private String usuario;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime fecha;// ver como se le pone al tipo date
	private String tabla;
	private String operacion;
	private String descripcion;

	public Integer getId() {
		return id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Auditoria [id=" + id + ", usuario=" + usuario + ", fecha=" + fecha + ", tabla=" + tabla + ", operacion="
				+ operacion + ", descripcion=" + descripcion + "]";
	}

	public Auditoria(Integer id, String usuario, LocalDateTime fecha, String tabla, String operacion,
			String descripcion) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.fecha = fecha;
		this.tabla = tabla;
		this.operacion = operacion;
		this.descripcion = descripcion;
	}

	public Auditoria() {
		super();
	}

}
