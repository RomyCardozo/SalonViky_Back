package com.salonViky.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salonViky.model.Cliente;
import com.salonViky.repository.ClienteRepository;

@Service
public class ClienteService {
	@Autowired
	ClienteRepository cr;
	
	//listar todo
	public List<Cliente> listar(){
		List<Cliente> result = new ArrayList<Cliente>();
		cr.findAll().forEach(result::add);;//retorna un iterable de user, debeemos cambiar
		//pq no sirve para web service// convert iterable collection java
		return result;
	}
	
    public List<Cliente> listarPorNombreApellidoyPaginacion(String nombre, Pageable pageable) {
        return cr.findByNombreLikeIgnoreCaseOrApellidoLikeIgnoreCase(nombre, nombre, pageable);
    }	
	
    public List<Cliente> listarPorNombre(String nombre) {
        return cr.findByNombre(nombre);
    }
	
    public List<Cliente> buscarPorNombreOApellido(String nombre) {
        return cr.findByNombreLikeIgnoreCaseOrApellidoLikeIgnoreCase(nombre, nombre);
    }
	//buscar por nombre

    //guardar o registrar
	public Cliente guardar(Cliente cliente) {
		return cr.save(cliente);
	}

	//buscar todo
    public List<Cliente> findAll() {
        return (List<Cliente>) cr.findAll();
    }

    //buscar id
    public Optional<Cliente> findById(Integer id) {
        return cr.findById(id);
    }

    //eliminar por id
    public void deleteById(Integer id) {
        cr.deleteById(id);
    }
    
    
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
