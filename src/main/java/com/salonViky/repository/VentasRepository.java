package com.salonViky.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salonViky.model.Ventas;

@Repository
public interface VentasRepository extends CrudRepository<Ventas, Integer>{
	
    List<Ventas> findByFechaBetween(LocalDateTime startDate, LocalDateTime endDate);
}
