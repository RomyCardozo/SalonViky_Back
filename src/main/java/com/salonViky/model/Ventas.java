package com.salonViky.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="ventas")
public class Ventas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//el identity = usa la pk generada en el postgress
	@Column(name="ventas_id")
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP) // timestamp es fecha y hora, le debemos especificar que es 
	@Column(name = "fecha", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
	@NotNull 
	private LocalDateTime fecha;
	
	@NotNull 
	private Double total;
	
	@NotNull @NotBlank 
	private String estado;
	
	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;

	@OneToMany(mappedBy = "ventas" ,cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VentaDetalle> detalles;
	
	
	public List<VentaDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<VentaDetalle> detalles) {
		this.detalles = detalles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Ventas() {
		super();
	}

	public Ventas(Integer id, LocalDateTime fecha, Double total, String estado, Usuario usuario, Cliente cliente) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.total = total;
		this.estado = estado;
		this.usuario = usuario;
		this.cliente = cliente;
	}

	public Ventas(Integer id) {
		super();
		this.id = id;
	}


	@Override
	public String toString() {
		return "Ventas [id=" + id + ", fecha=" + fecha + ", total=" + total + ", estado=" + estado + ", usuario="
				+ usuario + ", cliente=" + cliente + "]";
	}

	
	
}
