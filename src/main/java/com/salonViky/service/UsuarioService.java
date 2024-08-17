package com.salonViky.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salonViky.model.Usuario;
import com.salonViky.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository ur;
	
	public List<Usuario> listar(){
		List<Usuario> result = new ArrayList<Usuario>();
		ur.findAll().forEach(result::add);;//retorna un iterable de user, debeemos cambiar pq no sirve para web service// convert iterable collection java
		return result;
	}
	
    public List<Usuario> findAll() {
        return (List<Usuario>) ur.findAll();
    }
    
    public List<Usuario> listarPorNombrePaginacion(String nombre, Pageable pageable) {
        return ur.findByNombreLikeIgnoreCase(nombre, pageable);
    }	
	
    public List<Usuario> listarPorNombre(String nombre) {
        return ur.findByNombre(nombre);
    }
	

    //buscar id
    public Optional<Usuario> findById(Integer id) {
        return ur.findById(id);
    }

	
	public Usuario guardar(Usuario usuario) {
		return ur.save(usuario);
	}
	

    //eliminar por id
    public void deleteById(Integer id) {
        ur.deleteById(id);
    }
    	
	
	/*public List<Usuario> obtenerPorFiltroYPaginacion(
			String filtro, Integer pagina,
			Integer tamano){
		List<Usuario> usuarios = ur.byFilterPagination
				(filtro, pagina, tamano);
		//agregar o hacer mas cosas con servicios
		return usuarios;
	}*/
	
	/*public Usuario insertOrUpdate(Usuario usuarios) {
		//Validacion
		if (!(usuarios.getNombre() != null && usuarios.getNombre().length() > 3)) {
			throw new RuntimeException("Error en Nombre: ");
		}
		Usuario usuarioBuscar = ur.findById(usuarios.getId());
		return usuarioBuscar;
	}*/
}
