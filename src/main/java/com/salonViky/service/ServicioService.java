package com.salonViky.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salonViky.model.Servicio;
import com.salonViky.model.Servicio;
import com.salonViky.repository.ServicioRepository;

@Service
public class ServicioService {

	@Autowired
	ServicioRepository servicioRepository;
	
	
	public List<Servicio> listar(){
		List<Servicio> result = new ArrayList<Servicio>();
		servicioRepository.findAll().forEach(result::add);;//retorna un iterable de user, debeemos cambiar pq no sirve para web service// convert iterable collection java
		return result;
	}
    
    public List<Servicio> listarPorNombrePaginacion(String nombre, Pageable pageable) {
        return servicioRepository.findByNombreLikeIgnoreCase(nombre, pageable);
    }	
	
    public List<Servicio> listarPorNombre(String nombre) {
        return servicioRepository.findByNombre(nombre);
    }
	
    //este se utiliza para registrar y modificar
	public Servicio guardar(Servicio servicio) {
		return servicioRepository.save(servicio);
	}
	
	public Servicio actualizar(Servicio servicio) {
		return servicioRepository.save(servicio);
	}
	
	public Servicio eliminarActivo(Servicio servicio) {
		return servicioRepository.save(servicio);
	}

    //buscar id
 public Optional<Servicio> buscarPorId(Integer id) {
        return servicioRepository.findById(id);
    }

    //eliminar por id
    public void eliminarPorId(Integer id) {
        servicioRepository.deleteById(id);
    }
    
    public List<Object[]> obtenerResumenServicios(LocalDateTime startDate, LocalDateTime endDate) {
        return servicioRepository.findResumenServicios(startDate, endDate);
    }
	
}
