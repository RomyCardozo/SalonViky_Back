package com.salonViky.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salonViky.model.Cliente;
import com.salonViky.model.Servicio;
import com.salonViky.model.Usuario;
import com.salonViky.model.VentaDetalle;
import com.salonViky.model.Ventas;
import com.salonViky.repository.ClienteRepository;
import com.salonViky.repository.ServicioRepository;
import com.salonViky.repository.UsuarioRepository;
import com.salonViky.repository.VentasDetalleRepository;
import com.salonViky.service.VentaDetalleService;
import com.salonViky.service.VentasService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("ventas")
public class VentasController {
	
	@Autowired
	VentasService ventasService;
	
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	VentasDetalleRepository ventasDetalleRepository;

	@Autowired
	ServicioRepository servicioRepository;
	
	@Autowired
	VentaDetalleService ventaDetalleService;
	

	@GetMapping("listar")
	private Map<String, Object> listar() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ok",true);
		result.put("list", ventasService.listar());
		return result;
	}

	// Endpoint para obtener los detalles de una venta usando el ID de la venta
    @GetMapping("/listard/{id}")
    public ResponseEntity<List<VentaDetalle>> obtenerDetallesPorVenta(@PathVariable Integer id) {
        try {
            List<VentaDetalle> detalles = ventaDetalleService.getVentaDetallesPorVentaId(id);
            if (detalles.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(detalles);
            }
        } catch (Exception e) {
            // Log el error para tener información sobre lo que falló
            System.err.println("Error al obtener detalles de la venta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
	
	@GetMapping("listar/{id}")
	public ResponseEntity<Map<String, Object>> obtenerVenta(@PathVariable("id") Integer id) {
	    Optional<Ventas> ventaOptional = ventasService.findById(id);
	    Map<String, Object> result = new HashMap<>();
	  
	    if (ventaOptional.isEmpty()) {
	        throw new RuntimeException("Venta con id: " + id + " no encontrada");
	    } else {
	        Ventas venta = ventaOptional.get();
	        result.put("ok", true);
	        result.put("venta", venta);
	        result.put("detalles", venta.getDetalles()); // Verificar si los detalles están presentes
	        return ResponseEntity.ok(result);
	    }
	}

	
	@PostMapping("guardar")
	public ResponseEntity<Map<String, Object>> crearVentas1(@Valid @RequestBody Ventas ventas) {
	    Map<String, Object> result = new HashMap<>();

	    // Verificar si el Usuario y Cliente existen
	    Usuario usuario = usuarioRepository.findById(ventas.getUsuario().getId()).orElse(null);
	    Cliente cliente = clienteRepository.findById(ventas.getCliente().getId()).orElse(null);
	    if (usuario == null) {
	        throw new RuntimeException("El usuario proporcionado no existe.");
	    } else if (cliente == null) {
	        throw new RuntimeException("El cliente proporcionado no existe.");
	    }

	    // Asignar el usuario y cliente a la venta
	    ventas.setUsuario(usuario);
	    ventas.setCliente(cliente);

	    // Guardar la venta
	    
	    Ventas ventasGuardado = ventasService.guardar(ventas);

	    // Iterar sobre los detalles de la venta
	    for (VentaDetalle detalle : ventas.getDetalles()) {
	        // Verificar y cargar el servicio desde la base de datos
	        Servicio servicio = servicioRepository.findById(detalle.getServicio().getId())
	            .orElseThrow(() -> new RuntimeException("El servicio proporcionado no existe."));
	        // Asignar el servicio al detalle
	        detalle.setServicio(servicio);        
	        // Asignar la venta al detalle
	        detalle.setVentas(ventasGuardado);	        
	        // Guardar el detalle
	        ventasDetalleRepository.save(detalle);
	    }
	    // Preparar la respuesta
	    result.put("ok", true);
	    result.put("message", "Venta creada exitosamente.");
	    result.put("venta", ventasGuardado);
	    
	    return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@PostMapping("guardar1")
	public ResponseEntity<Map<String, Object>> crearVentas( @RequestBody Ventas ventas) {
	    Map<String, Object> result = new HashMap<>();

	    // Verificar si el Usuario y Cliente existen
	    try {
	        // Verificar y asignar usuario y cliente
	        Usuario usuario = usuarioRepository.findById(ventas.getUsuario().getId())
	                .orElseThrow(() -> new RuntimeException("El usuario proporcionado no existe."));
	        Cliente cliente = clienteRepository.findById(ventas.getCliente().getId())
	                .orElseThrow(() -> new RuntimeException("El cliente proporcionado no existe."));

	        ventas.setUsuario(usuario);
	        ventas.setCliente(cliente);

	        // Procesar detalles
	        for (VentaDetalle detalle : ventas.getDetalles()) {
	            Servicio servicio = servicioRepository.findById(detalle.getServicio().getId())
	                    .orElseThrow(() -> new RuntimeException("El servicio proporcionado no existe."));
	            detalle.setServicio(servicio);
	            detalle.setPrecioFromServicio();
	            detalle.calcularSubtotal();
	            detalle.setVentas(ventas);
	        }

	        // Calcular total
	        ventas.calcularTotal();

	        // Guardar la venta
	        Ventas ventasGuardado = ventasService.actualizar(ventas);

	        result.put("ok", true);
	        result.put("message", "Venta creada exitosamente.");
	        result.put("venta", ventasGuardado);

	        return ResponseEntity.status(HttpStatus.CREATED).body(result);

	    } catch (Exception e) {
	        result.put("ok", false);
	        result.put("message", "Error al crear la venta: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	    }
	}


	
	
	
	@PutMapping("editar2/{id}")
	public ResponseEntity<Map<String, Object>> editarVentas(@PathVariable("id") Integer id, @RequestBody Ventas ventas) {
	    Map<String, Object> resultado = new HashMap<>();

	    try {
	    	 Usuario usuario = usuarioRepository.findById(ventas.getUsuario().getId()).orElse(null);
	 	    Cliente cliente = clienteRepository.findById(ventas.getCliente().getId()).orElse(null);
	        // Verificar si la venta existe
	        Ventas ventaExistente = ventasService.findById(id)
	            .orElseThrow(() -> new RuntimeException("Ventas con id " + id + " no existe"));

	  
		     
		    // Actualizar los campos de la venta existente
	       
		    ventas.setUsuario(usuario);
		    ventas.setCliente(cliente);

		    
		    ventaExistente.actualizarDatos(ventas);
	        // Manejar los detalles de la venta
	        List<VentaDetalle> detallesActualizados = new ArrayList<>();

	        for (VentaDetalle detalleNuevo : ventas.getDetalles()) {
	            Servicio servicio = servicioRepository.findById(detalleNuevo.getServicio().getId())
	                .orElseThrow(() -> new RuntimeException("El servicio proporcionado no existe."));
	            
	            VentaDetalle detalle = new VentaDetalle();
	            detalle.setServicio(servicio);
	            detalle.setCantidad(detalleNuevo.getCantidad());
	            detalle.setPrecioFromServicio();
	            detalle.calcularSubtotal();
	            detalle.setVentas(ventaExistente);
	            detalle.setEstado("Activo"); // Asumiendo que "Activo" es el estado por defecto
	            
	            // Guardar el detalle usando el servicio
	            detalle = ventaDetalleService.guardarDetalle(detalle);
	            
	            detallesActualizados.add(detalle);
	        }

	        // Actualizar la lista de detalles
	        ventaExistente.getDetalles().clear();
	        ventaExistente.getDetalles().addAll(detallesActualizados);

	        // Calcular el total de la venta
	        ventaExistente.calcularTotal();

	        // Guardar la venta actualizada
	        Ventas ventaActualizada = ventasService.actualizar(ventaExistente);

	        // Preparar la respuesta
	        resultado.put("ok", true);
	        resultado.put("message", "Venta actualizada exitosamente.");
	        resultado.put("venta", ventaActualizada);

	        return ResponseEntity.status(HttpStatus.OK).body(resultado);

	    } catch (RuntimeException e) {
	        resultado.put("ok", false);
	        resultado.put("message", "Error al actualizar la venta: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultado);
	    }
	}
	


	@DeleteMapping(path ="delete/{codigo}")
	public void deleteVentas(
			@PathVariable("codigo") Integer codigo) {
		System.out.println("eliminar la venta con ID: " + codigo);
		Map<String, Object> result = new HashMap<>();
		
		if(ventasService.findById(codigo).isPresent()) {
			ventasDetalleRepository.deleteById(codigo);
			ventasService.deleteById(codigo);
			throw new RuntimeException("Venta eliminada");
			
		}

	}
	
	@PutMapping("eliminar/{id}")
	public ResponseEntity<Map<String, Object>> eliminarVentasActivo(@PathVariable Integer id) {
	    Map<String, Object> resultado = new HashMap<>();
	    Ventas ventas = ventasService.findById(id).orElse(null);
	    
	    if (ventas == null) {
	        throw new RuntimeException("La venta con id " + id + " no existe.");
	    }

	    // Actualizar el estado de cada VentaDetalle asociado a esta venta
	    for (VentaDetalle detalle : ventas.getDetalles()) {
	        detalle.setEstado("Inactivo");  // Actualizar el estado a "Inactivo"
	        ventasDetalleRepository.save(detalle);  // Guardar los cambios en cada detalle
	    }

	    // Marcar la venta como inactiva
	    ventas.setEstado("Inactivo");
	    ventasService.eliminarActivo(ventas);

	    // Preparar la respuesta
	    resultado.put("ok", true);
	    resultado.put("message", "Venta y sus detalles marcados como inactivos.");
	    
	    return ResponseEntity.ok(resultado);
	}
	

}
