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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salonViky.model.Cliente;
import com.salonViky.service.ClienteService;

import jakarta.validation.ConstraintViolationException;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("cliente")
public class ClienteController {
	
@Autowired
ClienteService cs;


//listar todo
@GetMapping("listar")
public Map<String, Object> listar() {
	Map<String, Object> result = new HashMap<String, Object>();
	result.put("ok",true);
	result.put("list", cs.listar());
	return result;
	}

//listar por nombre
@GetMapping("/nombre/{nombre}")
public Map<String, Object> obtenerClientePorNombre(@PathVariable("nombre") String nombre) {
    List<Cliente> clientes = cs.listarPorNombre(nombre);
    Map<String, Object> response = new HashMap<String, Object>();
    
    if (clientes.isEmpty()) {
        // Si no se encuentran clientes, retornar un error 404 con un mensaje
    	response.put("mensaje", "Cliente con nombre: " + nombre + " no encontrado");
    	
       
    } else {
        // Si se encuentran clientes, retornar un status 200 con la lista de clientes
    	response.put("mensaje", "Cliente con nombre: " + nombre + " encontrado");
        response.put("clientes", cs.listarPorNombre(nombre));
       
    }
    return response;
}
//obtener por nombre y apellido sin importar las mayusculas, minusculas ni nada 
@GetMapping("consultarNyA")
public Map<String, Object> listarPorLike( @RequestParam String q) {
	Map<String, Object> result = new HashMap<>();
    List<Cliente> clientes = cs.buscarPorNombreOApellido("%" + q + "%");

    if (clientes.isEmpty()) {
        result.put("ok", false);
        result.put("message", "No se encontraron registros con el nombre o apellido proporcionado.");
    } else {
        result.put("ok", true);
        result.put("list", clientes);
    }

    return result;
}


@GetMapping("consultarPaginado")
public Map<String, Object> listarPorLikePaginado( @RequestParam String q, Pageable pageable) {
	
	Map<String, Object> result = new HashMap<>();
    
	List<Cliente> clientes = cs.listarPorNombreApellidoyPaginacion("%" + q + "%", pageable);

    if (clientes.isEmpty()) {
        result.put("ok", false);
        result.put("message", "No se encontraron registros con el nombre o apellido proporcionado.");
    } else {
        result.put("ok", true);
        result.put("list", clientes);
    }

    return result;

}
//obtener por id 
@GetMapping("/obtener/{id}")
public ResponseEntity<Map<String, Object>> obtenerCliente(@PathVariable("id") Integer id) {
    Optional<Cliente> cliente = cs.findById(id);
    Map<String, Object> result = new HashMap<>();
    
    if (cliente.isEmpty()) {
        result.put("ok", false);
        result.put("message", "No se encontraron registros con el ID proporcionado.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    } else {
        result.put("ok", true);
        result.put("cliente", cliente.get());
        return ResponseEntity.ok(result);
    }
}

//registrar guardar
@PostMapping(path = "save")
public ResponseEntity<Map<String, Object>> crearCliente(@RequestBody Cliente cliente) {
    Map<String, Object> result = new HashMap<>();
    
    try {
        // Validar el cliente antes de guardarlo
        if (cliente == null) {
            result.put("ok", false);
            result.put("message", "No se proporcionaron datos válidos del cliente.");
            return ResponseEntity.badRequest().body(result);
        }
        
        // Guardar el cliente
        Cliente clienteGuardado = cs.guardar(cliente);
        
        // Preparar la respuesta
        result.put("ok", true);
        result.put("message", "Cliente creado exitosamente.");
        result.put("cliente", clienteGuardado);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    } catch (ConstraintViolationException e) {
        // Manejo de errores de validación
        result.put("ok", false);
        result.put("message", "Por favor, complete todos los campos obligatorios.");
        return ResponseEntity.badRequest().body(result);
    } catch (Exception e) {
        // Manejo de errores generales
        result.put("ok", false);
        result.put("message", "Hubo un problema al crear el cliente. Intente nuevamente.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
}



//Actualiza modifica 
@PutMapping(path = "update/{id}")
public ResponseEntity<Map<String, Object>> actualizarCliente(@PathVariable("id") Integer id, @RequestBody Cliente cliente) {
    Map<String, Object> result = new HashMap<>();
    
    // Verificar si el cliente existe
    if (cs.findById(id).isPresent()) {
        // Actualizar el cliente
        cliente.setId(id);  // Asegurarse de que el ID del cliente se mantiene
        Cliente actualizado = cs.guardar(cliente);
        
        // Preparar la respuesta exitosa
        result.put("ok", true);
        result.put("message", "Cliente actualizado exitosamente.");
        result.put("cliente", actualizado);
        return ResponseEntity.ok(result);
    } else {
        // Preparar la respuesta de error
        result.put("ok", false);
        result.put("message", "Cliente no encontrado.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
}

@DeleteMapping(path = "delete/{id}")
public ResponseEntity<Map<String, Object>> eliminarCliente(@PathVariable("id") Integer id) {
    Map<String, Object> result = new HashMap<>();
    
    if (cs.findById(id).isPresent()) {
        cs.deleteById(id);
        result.put("ok", true);
        result.put("message", "Cliente eliminado exitosamente.");
        
        return ResponseEntity.ok(result);
    } else {
        result.put("ok", false);
        result.put("message", "Cliente no encontrado.");
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
}



/*@GetMapping(path = "listar")
public List<Cliente> listaClientes(
    @RequestParam(name = "F", 
    defaultValue = "%%", 
    required = true) String filtro, 
    @RequestParam Integer page, 
    @RequestParam Integer size) {
    List<Cliente> listaClientes = clienteService.obtenerPorFiltroYPaginacion
    		(filtro, page, size);

    return listaClientes;
}	*/
/*@PostMapping(path ="save")
public Cliente crearAlumnos( @RequestBody Cliente clienteCrear){
	System.out.println("Crear Cliente" + clienteCrear);
	//realizar una operacion de insert en la bd
	return clienteCrear;
}

@PutMapping(path ="update/{codigo}")
public Cliente editarCliente(
		@PathVariable Integer codigo,//para indicar que vas a enviar ese dato en la url
		@RequestBody Cliente clienteEditar,
		@RequestHeader Map<String, String> header) {
	System.out.println("Editar el cliente con ID" + codigo);
	System.out.println("Autor Data " + clienteEditar);
	    clienteEditar.setId(codigo);
    
    return clienteEditar;
}

@DeleteMapping(path ="delete/{codigo}")
public void deleteCliente(
		@PathVariable Integer codigo,//para indicar que vas a enviar ese dato en la url
		@RequestHeader Map<String, String> header) {
	System.out.println("Cabezera, autorization: " + header );//ver si es necesario
	System.out.println("eliminar el cliente con ID " + codigo);

}*/
	
}
