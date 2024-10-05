package com.salonViky.repository;




import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.salonViky.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>, PagingAndSortingRepository<Usuario, Integer>{

	Optional<Usuario> findByNombre(String nombre);
	
	List<Usuario>findByNombreLikeIgnoreCase(String nombre, Pageable pageable);
	
	//Optional<Usuario> findByEmail(String email);
	
	
}
