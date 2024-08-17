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

import com.salonViky.model.Rol;
import com.salonViky.model.Usuario;
import com.salonViky.model.Usuario;
import com.salonViky.repository.RolRepository;
import com.salonViky.service.UsuarioService;

import jakarta.validation.ConstraintViolationException;



@RestController
@RequestMapping("usuario")
public class UsuarioController {
	
	@Autowired
	UsuarioService us;
	
    @Autowired
     RolRepository rolRepository;

	@GetMapping("listar")
	public Map<String, Object> listar() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ok",true);
		result.put("list", us.listar());
		return result;
		}
	
	@GetMapping("/nombre/{nombre}")
	public Map<String, Object> obtenerUsuarioPorNombre(@PathVariable("nombre") String nombre) {
	    List<Usuario> usuarios = us.listarPorNombre(nombre);
	    Map<String, Object> response = new HashMap<String, Object>();
	    
	    if (usuarios.isEmpty()) {
	        // Si no se encuentran Servicios, retornar un error 404 con un mensaje
	    	response.put("mensaje", "Usuario con nombre: " + nombre + " no encontrado");
	    	
	       
	    } else {
	        // Si se encuentran Servicios, retornar un status 200 con la lista de Servicios
	    	response.put("mensaje", "Usuario con nombre: " + nombre + " encontrado");
	        response.put("usuarios", us.listarPorNombre(nombre));
	       
	    }
	    return response;
	}
	
	@GetMapping("consultarPaginado")
	public Map<String, Object> listarPorLikePaginado( @RequestParam String q, Pageable pageable) {
		
		Map<String, Object> result = new HashMap<>();
	    
		List<Usuario> usuarios = us.listarPorNombrePaginacion("%" + q + "%", pageable);

	    if (usuarios.isEmpty()) {
	        result.put("ok", false);
	        result.put("message", "No se encontraron registros con el nombre proporcionado.");
	    } else {
	        result.put("ok", true);
	        result.put("list", usuarios);
	    }

	    return result;

	}
	
	@PostMapping(path = "save")
	public ResponseEntity<Map<String, Object>> crearUsuario(@RequestBody Usuario u) {
	    Map<String, Object> result = new HashMap<>();
	    
	    try {
	        // Validar el Usuario antes de guardarlo
	        if (u == null) {
	            result.put("ok", false);
	            result.put("message", "No se proporcionaron datos válidos del Usuario.");
	            return ResponseEntity.badRequest().body(result);
	        }
	        
	        Optional<Rol> rolExistente = rolRepository.findById(u.getRol().getId());
	        if (!rolExistente.isPresent()) {
	            result.put("ok", false);
	            result.put("message", "El Rol proporcionado no existe.");
	            return ResponseEntity.badRequest().body(result);
	        }
	        
	        // Guardar el usuario
	        Usuario UsuarioGuardado = us.guardar(u);
	        
	        // Preparar la respuesta
	        result.put("ok", true);
	        result.put("message", "Usuario creado exitosamente.");
	        result.put("Usuario", UsuarioGuardado);
	        return ResponseEntity.status(HttpStatus.CREATED).body(result);
	    } catch (ConstraintViolationException e) {
	        // Manejo de errores de validación
	        result.put("ok", false);
	        result.put("message", "Por favor, complete todos los campos obligatorios.");
	        return ResponseEntity.badRequest().body(result);
	    } catch (Exception e) {
	        // Manejo de errores generales
	        result.put("ok", false);
	        result.put("message", "Hubo un problema al crear el Usuario. Intente nuevamente.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
	    }
	}
	
	@PutMapping(path = "update/{id}")
	public ResponseEntity<Map<String, Object>> actualizarUsuario(@PathVariable("id") Integer id, @RequestBody Usuario usuario) {
	    Map<String, Object> result = new HashMap<>();
	    
	    // Verificar si el Usuario existe
	    if (us.findById(id).isPresent()) {
	        // Actualizar el Usuario
	    	usuario.setId(id);  // Asegurarse de que el ID del Usuario se mantiene
	        Usuario actualizado = us.guardar(usuario);
	        
	        // Preparar la respuesta exitosa
	        result.put("ok", true);
	        result.put("message", "Usuario actualizado exitosamente.");
	        result.put("Usuario", actualizado);
	        return ResponseEntity.ok(result);
	    } else {
	        // Preparar la respuesta de error
	        result.put("ok", false);
	        result.put("message", "Usuario no encontrado.");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
	    }
	}
	
	@DeleteMapping(path = "delete/{id}")
	public ResponseEntity<Map<String, Object>> eliminarServicio(@PathVariable("id") Integer id) {
	    Map<String, Object> result = new HashMap<>();
	    
	    if (us.findById(id).isPresent()) {
	        us.deleteById(id);
	        result.put("ok", true);
	        result.put("message", "usuario eliminado exitosamente.");
	        
	        return ResponseEntity.ok(result);
	    } else {
	        result.put("ok", false);
	        result.put("message", "usuario no encontrado.");
	        
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
	    }
	}
	
	
	/*
	
	@PostMapping(path ="save")
	public Usuario registrarUsuario( @RequestBody Usuario usuarioRegistro){
		System.out.println("Crear usuario: " + usuarioRegistro);
		//realizar una operacion de insert en la bd
		return usuarioRegistro;
	}	
	
	@PutMapping(path ="update/{codigo}")
	public Usuario modificarUsuarios(
			@PathVariable Integer codigo,//para indicar que vas a enviar ese dato en la url
			@RequestBody Usuario usuarioModificar,
			@RequestHeader Map<String, String> header) {
		System.out.println("Cabezera, autorization: " + header );
		System.out.println("Editar el permiso con ID: " + codigo);
		System.out.println("Autor Data: " + usuarioModificar);
		usuarioModificar.setId(codigo);
	    
	    return usuarioModificar;
	}	
	
	@DeleteMapping(path ="delete/{codigo}")
	public void deleteUsuarios(
			@PathVariable Integer codigo,//para indicar que vas a enviar ese dato en la url
			@RequestHeader Map<String, String> header) {
		System.out.println("Cabezera, autorization: " + header );
		System.out.println("eliminar el usuario con ID: " + codigo);

	}
	*/
	
}
