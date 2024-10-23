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
        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    public Cliente eliminarActivo(Cliente cliente) {
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

}
