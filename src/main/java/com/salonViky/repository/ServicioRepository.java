
package com.salonViky.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.salonViky.model.Servicio;


public interface ServicioRepository extends CrudRepository<Servicio, Integer>, PagingAndSortingRepository<Servicio, Integer> {
	
	List<Servicio> findByNombre(String nombre);
	
	List<Servicio> findByNombreLikeIgnoreCase(String nombre, Pageable pageable);
	
	@Query("SELECT s.nombre, COUNT(vd) AS cantidad, SUM(vd.precioUnitario * vd.cantidad) AS totalRecaudado " +
	           "FROM Servicio s JOIN s.detalles vd JOIN vd.ventas v " +
	           "WHERE v.fecha BETWEEN :startDate AND :endDate " +
	           "GROUP BY s.nombre")
	    List<Object[]> findResumenServicios(LocalDateTime startDate, LocalDateTime endDate);
}
