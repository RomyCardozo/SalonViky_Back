package com.salonViky.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salonViky.model.Ventas;

@Repository
public interface VentasRepository extends CrudRepository<Ventas, Integer>{

}
