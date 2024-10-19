package com.salonViky.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salonViky.model.Servicio;
import com.salonViky.service.ServicioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("servicio")
public class ServicioController {
	
@Autowired
ServicioService servicioService;
@GetMapping("listar")
private Map<String, Object> listar() {
	Map<String, Object> resultado = new HashMap<String, Object>();
	resultado.put("ok",true);
	resultado.put("list", servicioService.listar());
	return resultado;

}


//obtener por id 
@GetMapping("listar/{id}")
public ResponseEntity<Map<String, Object>> listarServicioPorId(@PathVariable("id") Integer id) {
  Optional<Servicio> servicio = servicioService.buscarPorId(id);
  Map<String, Object> resultado = new HashMap<>();
  
  if (servicio.isPresent()) {
      resultado.put("ok", true);
      resultado.put("Lista", servicio.get());
      return ResponseEntity.status(HttpStatus.OK).body(resultado);
  } else {
	  throw new RuntimeException("Servicio con id: " +id+ " no encontrado");
  }
}

//obtener por nombre sin importar las mayusculas, minusculas ni nada y ordenamiento con filtro

@GetMapping("listarPaginado")
public Map<String, Object> listarPorLikePaginado( @RequestParam String q, Pageable pageable) {
	
	Map<String, Object> resultado = new HashMap<>();
    
	List<Servicio> servicios = servicioService.listarPorNombrePaginacion("%" + q + "%", pageable);

    if (servicios.isEmpty()) {
        throw new RuntimeException("No existen resultados para el filtro proporcionado");

    } else {
        resultado.put("ok", true);
        resultado.put("Lista", servicios);
    }

    return resultado;

}

//registrar guardar
@PostMapping("guardar")
public ResponseEntity<Map<String, Object>> crearServicio( @Valid @RequestBody Servicio servicio) {
    Map<String, Object> resultado = new HashMap<>();
    
        
        // Guardar el Servicio
        Servicio servicioGuardado = servicioService.guardar(servicio);
        
        if(servicioGuardado == null) {
        	throw new RuntimeException("Error al guardar");
        }else {
        // Preparar la respuesta
        resultado.put("ok", true);
        resultado.put("message", "Servicio creado exitosamente.");
        resultado.put("servicio", servicioGuardado);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        }
}



//Actualiza modifica 
@PutMapping("editar/{id}")
public ResponseEntity<Map<String, Object>> actualizarServicio(@PathVariable("id") Integer id,@Valid @RequestBody Servicio servicio) {
    Map<String, Object> resultado = new HashMap<>();
    
    // Verificar si el Servicio existe
    if (servicioService.buscarPorId(id).isPresent()) {
        // Actualizar el Servicio
    	servicio.setId(id);  // Asegurarse de que el ID del Servicio se mantiene
        Servicio actualizado = servicioService.actualizar(servicio);
        
        // Preparar la respuesta exitosa
        resultado.put("ok", true);
        resultado.put("message", "Servicio actualizado exitosamente.");
        resultado.put("servicio", actualizado);
        return ResponseEntity.ok(resultado);
    } else {
    	throw new RuntimeException("Servicio con id " + id + " no existe");
    }
}

//"elimina" temporalmente cambiando el estado a "inactivo" 
@PutMapping("eliminar/{id}")
public ResponseEntity<Map<String, Object>> eliminarServicioActivo(@PathVariable("id") Integer id) {
    Map<String, Object> resultado = new HashMap<>();
    
     Servicio servicio = servicioService.buscarPorId(id).orElse(null);
    if (servicio != null ) {
    	servicio.setEstado("Inactivo");
    	servicioService.eliminarActivo(servicio) ;
    	    resultado.put("ok", true);
    	    resultado.put("message", "Servicio marcado como inactivo");
    	    return ResponseEntity.ok(resultado);
    } else {
        throw new RuntimeException("Id no fue encontrado");
    }
}


@DeleteMapping("eliminar/{id}")
public ResponseEntity<Map<String, Object>> eliminarServicio(@PathVariable("id") Integer id) {
    Map<String, Object> resultado = new HashMap<>();
    
    if (servicioService.buscarPorId(id).isPresent()) {
        servicioService.eliminarPorId(id);
        resultado.put("ok", true);
        resultado.put("message", "Servicio eliminado exitosamente.");
        return ResponseEntity.ok(resultado);
    } else {
    	throw new RuntimeException("Id no fue encontrado");
    }
}

	



}
