package com.salonViky.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salonViky.model.Cliente;
import com.salonViky.repository.ClienteRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class ClienteService {
	@Autowired
	ClienteRepository clienteRepository;
	
	
	//listar todo
	public List<Cliente> listar(){
		List<Cliente> resultado = new ArrayList<Cliente>();
		clienteRepository.findAll().forEach(resultado::add);;//retorna un iterable de user, debeemos cambiar
		//pq no sirve para web service// convert iterable collection java
		return resultado;
	}
	
    public List<Cliente> listarPorNombreApellidoyPaginacion(String nombre, Pageable pageable) {
        return clienteRepository.findByNombreLikeIgnoreCaseOrApellidoLikeIgnoreCase(nombre, nombre, pageable);
    }	
	
    public Cliente guardar(Cliente cliente) {
        //auditoriaservice.guardar(usuario,"edicion de usuario", "cliente", cliente.id)
        return clienteRepository.save(cliente);
    }

    //buscar id
    public Optional<Cliente> buscarPorId(Integer id) {
        return clienteRepository.findById(id);
    }

    //eliminar por id
    public void eliminarPorId(Integer id) {
        clienteRepository.deleteById(id);
    }
    /* public List<Cliente> listarPorNombre(String nombre) {
    return cr.findByNombre(nombre);
}

public List<Cliente> buscarPorNombreOApellido(String nombre) {
    return cr.findByNombreLikeIgnoreCaseOrApellidoLikeIgnoreCase(nombre, nombre);
}*/
//buscar por nombre

//guardar o registrar
/*public Cliente guardar(Cliente cliente) {
	return cr.save(cliente);
}*/
    
	/*public List<Cliente> obtenerPorFiltroYPaginacion(
			String filtro, Integer pagina,
			Integer tamano){
		List<Cliente> clientes = cr.byFilterPagination
				(filtro, pagina, tamano);
		//agregar o hacer mas cosas con clientes
		return clientes;
	}*/
	
	
	/*public Cliente insertOrUpdate(Cliente cliente) {
		//Validacion
		if (!(cliente.getNombre() != null && cliente.getNombre().length() > 3)) {
			throw new RuntimeException("Error en Nombre de cliente");
		}
		Cliente clienteBuscar = cr.findById(cliente.getId());
		return clienteBuscar;
	}*/

}
