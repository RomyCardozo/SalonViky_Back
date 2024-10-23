package com.salonViky.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salonViky.model.Rol;
import com.salonViky.repository.RolRepository;

@Service
public class RolService {

	@Autowired
	RolRepository rolRepository;
	
	public List<Rol> listar(){
		List<Rol> result = new ArrayList<Rol>();
		rolRepository.findAll().forEach(result::add);;//retorna un iterable de user, debeemos cambiar
		//pq no sirve para web service// convert iterable collection java
		return result;
	}
	
}
