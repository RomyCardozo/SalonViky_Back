
package com.salonViky.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.salonViky.model.Servicio;


public interface ServicioRepository extends CrudRepository<Servicio, Integer>, PagingAndSortingRepository<Servicio, Integer> {
	
	List<Servicio> findByNombre(String nombre);
	
	List<Servicio> findByNombreLikeIgnoreCase(String nombre, Pageable pageable);
}
