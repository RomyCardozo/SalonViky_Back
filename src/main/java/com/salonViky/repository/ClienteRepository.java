package com.salonViky.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.salonViky.model.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Integer>, PagingAndSortingRepository<Cliente, Integer>{
	
	List<Cliente> findByNombre(String nombre);
	
	List<Cliente>findByNombreLikeIgnoreCaseOrApellidoLikeIgnoreCase(String nombre, String apellido);
	
	List<Cliente>findByNombreLikeIgnoreCaseOrApellidoLikeIgnoreCase(String nombre, String apellido, Pageable pageable);

}
