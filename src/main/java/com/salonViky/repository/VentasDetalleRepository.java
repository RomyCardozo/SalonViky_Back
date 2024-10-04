package com.salonViky.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salonViky.model.VentaDetalle;
import com.salonViky.model.Ventas;

@Repository
public interface VentasDetalleRepository extends CrudRepository<VentaDetalle,Integer>{
	
	// Método para encontrar los detalles de una venta utilizando el campo 'ventas'
    List<VentaDetalle> findByVentas(Ventas ventas);

}
