package com.salonViky.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salonViky.model.VentaDetalle;

@Repository
public interface VentasDetalleRepository extends CrudRepository<VentaDetalle,Integer>{


}
