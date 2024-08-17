package com.salonViky.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salonViky.model.Servicio;
import com.salonViky.model.Ventas;
import com.salonViky.service.VentasService;

import jakarta.validation.ConstraintViolationException;


@RestController
@RequestMapping("ventas")
public class VentasController {
	
	@Autowired
	VentasService vs;
	

	@GetMapping("listar")
	private Map<String, Object> listar() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ok",true);
		result.put("list", vs.listar());
		return result;
	}
	
	
	@GetMapping("/obtener/{id}")
	public ResponseEntity<Map<String, Object>> obtenerVenta(@PathVariable("id") Integer id) {
	  Optional<Ventas> servicio = vs.findById(id);
	  Map<String, Object> result = new HashMap<>();
	  
	  if (servicio.isEmpty()) {
	      result.put("ok", false);
	      result.put("message", "No se encontraron registros con el ID proporcionado.");
	      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
	  } else {
	      result.put("ok", true);
	      result.put("venta", servicio.get());
	      return ResponseEntity.ok(result);
	  }
	}
	
	@PostMapping(path = "save")
	public ResponseEntity<Map<String, Object>> crearVentas(@RequestBody Ventas ventas) {
	    Map<String, Object> result = new HashMap<>();
	    
	    try {
	        // Validar el Servicio antes de guardarlo
	        if (ventas == null) {
	            result.put("ok", false);
	            result.put("message", "No se proporcionaron datos válidos del ventas.");
	            return ResponseEntity.badRequest().body(result);
	        }
	        
	        // Guardar el Servicio
	        Ventas ventasGuardado = vs.guardar(ventas);
	        
	        // Preparar la respuesta
	        result.put("ok", true);
	        result.put("message", "Venta creada exitosamente.");
	        result.put("servicio", ventasGuardado);
	        return ResponseEntity.status(HttpStatus.CREATED).body(result);
	    } catch (ConstraintViolationException e) {
	        // Manejo de errores de validación
	        result.put("ok", false);
	        result.put("message", "Por favor, complete todos los campos obligatorios.");
	        return ResponseEntity.badRequest().body(result);
	    } catch (Exception e) {
	        // Manejo de errores generales
	        result.put("ok", false);
	        result.put("message", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
	    }
	}

	
	
	
	/*@GetMapping("consultarPaginado")
	public Map<String, Object> listarPorLikePaginado( @RequestParam LocalDateTime q, Pageable pageable) {
		
		Map<String, Object> result = new HashMap<>();
	    
		List<Ventas> ventas = vs.listarPorFechaPaginacion(q , pageable);

	    if (ventas.isEmpty()) {
	        result.put("ok", false);
	        result.put("message", "No se encontraron registros con la fecha proporcionado.");
	    } else {
	        result.put("ok", true);
	        result.put("list", ventas);
	    }

	    return result;

	}*/
	

	/*@PostMapping(path ="save")
	public Ventas crearVentas( @RequestBody Ventas ventasRegistrar){
		System.out.println("Registrar venta" + ventasRegistrar);
		//realizar una operacion de insert en la bd
		return ventasRegistrar;
	}
	@PutMapping(path ="update/{codigo}")
	public Ventas modificarVentas(
			@PathVariable Integer codigo,//para indicar que vas a enviar ese dato en la url
			@RequestBody Ventas ventasModificar,
			@RequestHeader Map<String, String> header) {
		System.out.println("Cabezera, autorization: " + header );
		System.out.println("Modificar la venta con ID: " + codigo);
		System.out.println(" Data: " + ventasModificar);
		ventasModificar.setId(codigo);
	    
	    return ventasModificar;
	}

	@DeleteMapping(path ="delete/{codigo}")
	public void deleteVentas(
			@PathVariable Integer codigo,//para indicar que vas a enviar ese dato en la url
			@RequestHeader Map<String, String> header) {
		System.out.println("eliminar la venta con ID: " + codigo);

	}
	
	*/
	
	/*@GetMapping(path = "listar")
	public List<Ventas> listaVentas(
	    @RequestParam(name = "F", 
	    defaultValue = "%%", 
	    required = true) String filtro, 
	    @RequestParam Integer page, 
	    @RequestParam Integer size) {
	    List<Ventas> listaVentas = ventasService.obtenerPorFiltroYPaginacion
	    		(filtro, page, size);

	    return listaVentas;
	}*/	
	/*@GetMapping("/venta/{id}/total")
	public double obtenerTotalVenta(@PathVariable("id") Integer id) {
	    return vs.calcularTotalVenta(id);
	}*/
	
}
