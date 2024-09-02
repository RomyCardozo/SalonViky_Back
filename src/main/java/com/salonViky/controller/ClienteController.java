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

import com.salonViky.model.Cliente;
import com.salonViky.service.ClienteService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("cliente")
public class ClienteController {
	
@Autowired
ClienteService clienteService;


//listar todo
@GetMapping("listar")
public Map<String, Object> listar() {
	Map<String, Object> resultado = new HashMap<String, Object>();
	resultado.put("ok",true);
	resultado.put("list", clienteService.listar());
	return resultado;
	}

@GetMapping("listarPaginado")
public Map<String, Object> listarPorLikePaginado( @RequestParam String q, Pageable pageable) {
	
	Map<String, Object> resultado = new HashMap<>();
    
	List<Cliente> clientes = clienteService.listarPorNombreApellidoyPaginacion("%" + q + "%", pageable);

    if (clientes.isEmpty()) {
        throw new RuntimeException("No existen resultados para el filtro proporcionado");
    } else {
    	resultado.put("ok", true);
    	resultado.put("lista", clientes);
    }

    return resultado;

}
//obtener por id 
@GetMapping("listar/{id}")
public ResponseEntity<Map<String, Object>> listarPorId(@PathVariable("id") Integer id) {
    Optional<Cliente> cliente = clienteService.buscarPorId(id);
    Map<String, Object> resultado = new HashMap<>();
    
	if(cliente.isPresent()) {
		resultado.put("ok", true);
		resultado.put("Lista", cliente.get());
		return ResponseEntity.status(HttpStatus.OK).body(resultado);
	}else {
		throw new RuntimeException("Cliente con id: " +id+ " no encontrado");
	}
}

//registrar guardar
@PostMapping("guardar")
public ResponseEntity<Map<String, Object>> crearCliente(@Valid @RequestBody Cliente cliente) {
    Map<String, Object> resultado = new HashMap<>();
        
        // Guardar el cliente
        Cliente clienteGuardado = clienteService.guardar(cliente);
        
        if(clienteGuardado == null) {
        	throw new RuntimeException("Error al guardar");
        }
        // Preparar la respuesta
        resultado.put("ok", true);
        resultado.put("Mensaje", "Cliente creado exitosamente.");
        resultado.put("cliente", clienteGuardado);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
  
    
}



//Actualiza modifica 
@PutMapping("editar/{id}")
public ResponseEntity<Map<String, Object>> editarCliente(@PathVariable("id") Integer id, @Valid @RequestBody Cliente cliente) {
    Map<String, Object> result = new HashMap<>();
    
    // Verificar si el cliente existe
    if (clienteService.buscarPorId(id).isPresent()) {
        // Actualizar el cliente
        cliente.setId(id);  // Asegurarse de que el ID del cliente se mantiene
        // Preparar la respuesta exitosa
        result.put("ok", true);
        result.put("Mensaje", "Cliente actualizado exitosamente.");
        result.put("cliente", clienteService.guardar(cliente));
        return ResponseEntity.ok(result);
    } else {
    	throw new RuntimeException("Producto con id " + id + " no existe");
    }
}

@DeleteMapping("eliminar/{id}")
public ResponseEntity<Map<String, Object>> eliminarCliente(@PathVariable("id") Integer id) {
    Map<String, Object> result = new HashMap<>();
    
    if (clienteService.buscarPorId(id).isPresent()) {
        clienteService.eliminarPorId(id);
        result.put("ok", true);
        result.put("Mensaje", "Cliente eliminado exitosamente.");
        return ResponseEntity.ok(result);
    } else {
    	throw new RuntimeException("Id no fue encontrado");
    }
}
//anular cliente // en el front mostrar clientes con activo nomas
@PutMapping("eliminar/{id}")
public ResponseEntity<Map<String, Object>> eliminarClienteActivo(@PathVariable Integer id) {
    Map<String, Object> resultado = new HashMap<>();
    Cliente cliente = clienteService.buscarPorId(id).orElse(null);
    
    if (cliente == null) {
        throw new RuntimeException("id no existe");
    }
    
    cliente.setEstado("inactivo");
    clienteService.guardar(cliente);  
    resultado.put("ok", true);
    resultado.put("message", "Cliente marcado como inactivo");
    return ResponseEntity.ok(resultado);
}

	
}
