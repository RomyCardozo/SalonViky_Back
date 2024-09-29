package com.salonViky.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name="venta_detalles")
public class VentaDetalle {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="venta_detalle_id")
	private Integer id;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="venta_id")
	private Ventas ventas;//fk
	
	@ManyToOne
	@JoinColumn(name="servicio_id")	
	private Servicio servicio;//fk
	
	@NotNull 
	private Integer cantidad;
	
	@Column(name="precio_unitario")
	@NotNull
	private Double precioUnitario;
	
	@Column(name="subtotal")
	@NotNull 
	private Double subTotal;
	
	@NotNull @NotBlank @NotEmpty
	private String estado;

    public void actualizarDatos(VentaDetalle nuevoDetalle) {
        this.setServicio(nuevoDetalle.getServicio());
        this.setCantidad(nuevoDetalle.getCantidad());
        this.setPrecioFromServicio();
        this.calcularSubtotal();
    }

    public void setPrecioFromServicio() {
        if (this.servicio != null) {
            this.precioUnitario = this.servicio.getPrecio();
        }
    }

    public void calcularSubtotal() {
        if (this.cantidad != null && this.precioUnitario != null) {
            this.subTotal = this.cantidad * this.precioUnitario;
        }
    }
	public VentaDetalle() {
		super();
	}


	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public Ventas getVentas() {
		return ventas;
	}




	public void setVentas(Ventas ventas) {
		this.ventas = ventas;
	}




	public Servicio getServicio() {
		return servicio;
	}




	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}




	public Integer getCantidad() {
		return cantidad;
	}




	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}




	public Double getPrecioUnitario() {
		return precioUnitario;
	}




	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}




	public Double getSubTotal() {
		return subTotal;
	}




	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}




	public String getEstado() {
		return estado;
	}




	public void setEstado(String estado) {
		this.estado = estado;
	}




	public VentaDetalle(Integer id) {
		super();
		this.id = id;
	}




	public VentaDetalle(Integer id, Ventas ventas, Servicio servicio, Integer cantidad, Double precioUnitario,
			Double subTotal, String estado) {
		super();
		this.id = id;
		this.ventas = ventas;
		this.servicio = servicio;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.subTotal = subTotal;
		this.estado = estado;
	}




	@Override
	public String toString() {
		return "VentaDetalle [id=" + id + ", ventas=" + ventas + ", servicio=" + servicio + ", cantidad=" + cantidad
				+ ", precioUnitario=" + precioUnitario + ", subTotal=" + subTotal + ", estado=" + estado + "]";
	}



	
	
	
}
