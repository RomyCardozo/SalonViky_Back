package com.salonViky.service;

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
	ServicioRepository sr;
	
	
	public List<Servicio> listar(){
		List<Servicio> result = new ArrayList<Servicio>();
		sr.findAll().forEach(result::add);;//retorna un iterable de user, debeemos cambiar pq no sirve para web service// convert iterable collection java
		return result;
	}
	
    public List<Servicio> findAll() {
        return (List<Servicio>) sr.findAll();
    }
    
    public List<Servicio> listarPorNombrePaginacion(String nombre, Pageable pageable) {
        return sr.findByNombreLikeIgnoreCase(nombre, pageable);
    }	
	
    public List<Servicio> listarPorNombre(String nombre) {
        return sr.findByNombre(nombre);
    }
	
    //este se utiliza para registrar y modificar
	public Servicio guardar(Servicio servicio) {
		return sr.save(servicio);
	}

    //buscar id
    public Optional<Servicio> findById(Integer id) {
        return sr.findById(id);
    }

    //eliminar por id
    public void deleteById(Integer id) {
        sr.deleteById(id);
    }
    
	
}
