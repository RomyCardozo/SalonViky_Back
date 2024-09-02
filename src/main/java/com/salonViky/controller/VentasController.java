package com.salonViky.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.salonViky.service.VentasService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("ventas")
public class VentasController {
	
	@Autowired
	VentasService vs;
	
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	VentasDetalleRepository ventasDetalleRepository;

	@Autowired
	ServicioRepository servicioRepository;
	

	@GetMapping("listar")
	private Map<String, Object> listar() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ok",true);
		result.put("list", vs.listar());
		return result;
	}
	
	@GetMapping("listar/{id}")
	public ResponseEntity<Map<String, Object>> obtenerVenta(@PathVariable("id") Integer id) {
	    Optional<Ventas> ventaOptional = vs.findById(id);
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
	public ResponseEntity<Map<String, Object>> crearVentas( @Valid @RequestBody Ventas ventas) {
	    Map<String, Object> result = new HashMap<>();
	        
	        Usuario usuario = usuarioRepository.findById(ventas.getUsuario().getId()).orElse(null);
	        Cliente cliente = clienteRepository.findById(ventas.getCliente().getId()).orElse(null);
	       if(usuario == null) {
	    	   throw new RuntimeException("usuario proporcionado no existe ");
	       }else if (cliente == null) {
	    	   throw new RuntimeException("cliente proporcionado no existe ");
	       }
	        
	        
	        // Guardar el Servicio
	        Ventas ventasGuardado = vs.guardar(ventas);
	        
	        for(VentaDetalle detalle : ventas.getDetalles()) {
	        	detalle.setVentas(ventasGuardado);
	        	ventasDetalleRepository.save(detalle);
	        	
	        	
	        }
	        
	        
	        // Preparar la respuesta
	        result.put("ok", true);
	        result.put("message", "Venta creada exitosamente.");
	        result.put("servicio", ventasGuardado);
	        ventas.setUsuario(usuario);
	        ventas.setCliente(cliente);
	        return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@PostMapping("guardar1")
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
	    Ventas ventasGuardado = vs.guardar(ventas);

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
	
	@DeleteMapping(path ="delete/{codigo}")
	public void deleteVentas(
			@PathVariable("codigo") Integer codigo) {
		System.out.println("eliminar la venta con ID: " + codigo);
		Map<String, Object> result = new HashMap<>();
		
		if(vs.findById(codigo).isPresent()) {
			ventasDetalleRepository.deleteById(codigo);
			vs.deleteById(codigo);
			throw new RuntimeException("Venta eliminada");
			
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
