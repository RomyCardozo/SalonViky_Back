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
import com.salonViky.repository.RolRepository;
import com.salonViky.service.UsuarioService;

import jakarta.validation.Valid;



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
	

	@GetMapping("consultarPaginado")
	public Map<String, Object> listarPorLikePaginado( @RequestParam String q, Pageable pageable) {
		
		Map<String, Object> resultado = new HashMap<>();
	    
		List<Usuario> usuarios = us.listarPorNombrePaginacion("%" + q + "%", pageable);

	    if (usuarios.isEmpty()) {
	        throw new RuntimeException(" No existen los datos con los filtros proporcionados");
	    } else {
	        resultado.put("ok", true);
	        resultado.put("list", usuarios);
	    }

	    return resultado;

	}
	
	@PostMapping("guardar")
	public ResponseEntity<Map<String, Object>> guardarUsuario( @Valid @RequestBody Usuario usuario) {
	    Map<String, Object> result = new HashMap<>();
	    
	        
	        Optional<Rol> rolExistente = rolRepository.findById(usuario.getRol().getId());
	        if (!rolExistente.isPresent()) {
	            throw new RuntimeException("Rol proporcionado no existe ");
	        }else {
	        	// Asignar el rol encontrado al usuario
	            usuario.setRol(rolExistente.get());
	        // Guardar el usuario
	        Usuario UsuarioGuardado = us.guardar(usuario);
	             
	        // Preparar la respuesta
	        result.put("ok", true);
	        result.put("message", "Usuario creado exitosamente.");
	        result.put("Usuario", UsuarioGuardado);
	        return ResponseEntity.status(HttpStatus.CREATED).body(result);}
	}
	
	@PutMapping("actualizar/{id}")
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
	    	throw new RuntimeException("Usuario no encontrado.");
	    }
	}
	
	@DeleteMapping("eliminar/{id}")
	public ResponseEntity<Map<String, Object>> eliminarServicio(@PathVariable("id") Integer id) {
	    Map<String, Object> result = new HashMap<>();
	    
	    if (us.findById(id).isPresent()) {
	        us.deleteById(id);
	        result.put("ok", true);
	        result.put("message", "usuario eliminado exitosamente.");
	        return ResponseEntity.ok(result);
	    } else {
	    	throw new RuntimeException("id no existe");
	    }
	}
	
	//anular cliente // en el front mostrar clientes con activo nomas
	@PutMapping("eliminar/{id}")
	public ResponseEntity<Map<String, Object>> eliminarClienteActivo(@PathVariable Integer id) {
	    Map<String, Object> resultado = new HashMap<>();
	    Usuario usuario = us.findById(id).orElse(null);
	    
	    if (usuario == null) {
	        throw new RuntimeException("id no existe");
	    }
	    usuario.setEstado("Inactivo");
	    us.guardar(usuario);  
	    resultado.put("ok", true);
	    resultado.put("message", "usuario marcado como inactivo");
	    return ResponseEntity.ok(resultado);
	}	
	
	
	/*	@GetMapping("/nombre/{nombre}")
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
	}*/
	
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
