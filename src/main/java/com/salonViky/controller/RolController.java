package com.salonViky.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salonViky.model.Rol;
import com.salonViky.service.RolService;


@RestController
@RequestMapping("rol")
public class RolController {
	@Autowired
	RolService rolService;
	
	@GetMapping("listar")
	private Map<String, Object> listar() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ok",true);
		result.put("list", rolService.listar());
		return result;
	}

	@PostMapping("guardar")
	public Rol crearRol( @RequestBody Rol rolCrear){
		System.out.println("Crear Rol" + rolCrear);
		//realizar una operacion de insert en la bd
		return rolCrear;
	}
}
