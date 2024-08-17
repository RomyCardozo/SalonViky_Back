package com.salonViky.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping("servicio")
public class ServicioController {
	
@Autowired
ServicioService servicioService;

@GetMapping("listar")
private Map<String, Object> listar() {
	Map<String, Object> result = new HashMap<String, Object>();
	result.put("ok",true);
	result.put("list", servicioService.listar());
	return result;

}

@GetMapping("/nombre/{nombre}")
public Map<String, Object> obtenerServicioPorNombre(@PathVariable("nombre") String nombre) {
    List<Servicio> servicios = servicioService.listarPorNombre(nombre);
    Map<String, Object> response = new HashMap<String, Object>();
    
    if (servicios.isEmpty()) {
        // Si no se encuentran Servicios, retornar un error 404 con un mensaje
    	response.put("mensaje", "Servicio con nombre: " + nombre + " no encontrado");
    	
       
    } else {
        // Si se encuentran Servicios, retornar un status 200 con la lista de Servicios
    	response.put("mensaje", "Servicio con nombre: " + nombre + " encontrado");
        response.put("servicios", servicioService.listarPorNombre(nombre));
       
    }
    return response;
}


//obtener por id 
@GetMapping("/obtener/{id}")
public ResponseEntity<Map<String, Object>> obtenerServicio(@PathVariable("id") Integer id) {
  Optional<Servicio> servicio = servicioService.findById(id);
  Map<String, Object> result = new HashMap<>();
  
  if (servicio.isEmpty()) {
      result.put("ok", false);
      result.put("message", "No se encontraron registros con el ID proporcionado.");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
  } else {
      result.put("ok", true);
      result.put("servicio", servicio.get());
      return ResponseEntity.ok(result);
  }
}

//obtener por nombre sin importar las mayusculas, minusculas ni nada y ordenamiento con filtro

@GetMapping("consultarPaginado")
public Map<String, Object> listarPorLikePaginado( @RequestParam String q, Pageable pageable) {
	
	Map<String, Object> result = new HashMap<>();
    
	List<Servicio> servicios = servicioService.listarPorNombrePaginacion("%" + q + "%", pageable);

    if (servicios.isEmpty()) {
        result.put("ok", false);
        result.put("message", "No se encontraron registros con el nombre proporcionado.");
    } else {
        result.put("ok", true);
        result.put("list", servicios);
    }

    return result;

}

//registrar guardar
@PostMapping(path = "save")
public ResponseEntity<Map<String, Object>> crearServicio(@RequestBody Servicio servicio) {
    Map<String, Object> result = new HashMap<>();
    
    try {
        // Validar el Servicio antes de guardarlo
        if (servicio == null) {
            result.put("ok", false);
            result.put("message", "No se proporcionaron datos válidos del Servicio.");
            return ResponseEntity.badRequest().body(result);
        }
        
        // Guardar el Servicio
        Servicio ServicioGuardado = servicioService.guardar(servicio);
        
        // Preparar la respuesta
        result.put("ok", true);
        result.put("message", "Servicio creado exitosamente.");
        result.put("servicio", ServicioGuardado);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    } catch (ConstraintViolationException e) {
        // Manejo de errores de validación
        result.put("ok", false);
        result.put("message", "Por favor, complete todos los campos obligatorios.");
        return ResponseEntity.badRequest().body(result);
    } catch (Exception e) {
        // Manejo de errores generales
        result.put("ok", false);
        result.put("message", "Hubo un problema al crear el Servicio. Intente nuevamente.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
}



//Actualiza modifica 
@PutMapping(path = "update/{id}")
public ResponseEntity<Map<String, Object>> actualizarServicio(@PathVariable("id") Integer id, @RequestBody Servicio servicio) {
    Map<String, Object> result = new HashMap<>();
    
    // Verificar si el Servicio existe
    if (servicioService.findById(id).isPresent()) {
        // Actualizar el Servicio
    	servicio.setId(id);  // Asegurarse de que el ID del Servicio se mantiene
        Servicio actualizado = servicioService.guardar(servicio);
        
        // Preparar la respuesta exitosa
        result.put("ok", true);
        result.put("message", "Servicio actualizado exitosamente.");
        result.put("servicio", actualizado);
        return ResponseEntity.ok(result);
    } else {
        // Preparar la respuesta de error
        result.put("ok", false);
        result.put("message", "Servicio no encontrado.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
}

@DeleteMapping(path = "delete/{id}")
public ResponseEntity<Map<String, Object>> eliminarServicio(@PathVariable("id") Integer id) {
    Map<String, Object> result = new HashMap<>();
    
    if (servicioService.findById(id).isPresent()) {
        servicioService.deleteById(id);
        result.put("ok", true);
        result.put("message", "Servicio eliminado exitosamente.");
        
        return ResponseEntity.ok(result);
    } else {
        result.put("ok", false);
        result.put("message", "Servicio no encontrado.");
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
}

	

/*@GetMapping("listar")
public List<Servicio> listarServicios(){
	return new ArrayList<Servicio>();
}*/

/*@PostMapping(path ="save")
public Servicio crearServicios( @RequestBody Servicio servicioCrear){
	System.out.println("Crear servicio" + servicioCrear);
	//realizar una operacion de insert en la bd
	return servicioCrear;
}
@PutMapping(path ="update/{codigo}")
public Servicio editarServicios(
		@PathVariable Integer codigo,//para indicar que vas a enviar ese dato en la url
		@RequestBody Servicio servicioEditar,
		@RequestHeader Map<String, String> header) {
	System.out.println("Cabezera, autorization: " + header );
	System.out.println("Editar el servicio con ID" + codigo);
	System.out.println("Autor Data" + servicioEditar);
	servicioEditar.setId(codigo);
    
    return servicioEditar;
}

@DeleteMapping(path ="delete/{codigo}")
public void deleteServicio(
		@PathVariable Integer codigo,//para indicar que vas a enviar ese dato en la url
		@RequestHeader Map<String, String> header) {
	System.out.println("Cabezera, autorization: " + header );
	System.out.println("eliminar el servicio con ID " + codigo);

}*/



}
