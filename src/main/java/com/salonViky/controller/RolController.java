package com.salonViky.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salonViky.model.Rol;
import com.salonViky.service.RolService;


@RestController
@RequestMapping("rol")
public class RolController {
	@Autowired
	RolService rs;
	
	@GetMapping("listar")
	private Map<String, Object> listar() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ok",true);
		result.put("list", rs.listar());
		return result;
	}

/*	@PostMapping(path ="save")
	public Rol crearRol( @RequestBody Rol rolCrear){
		System.out.println("Crear Rol" + rolCrear);
		//realizar una operacion de insert en la bd
		return rolCrear;
	}*/
	/*@PutMapping(path ="update/{codigo}")
	public Rol editarRol(
			@PathVariable Integer codigo,//para indicar que vas a enviar ese dato en la url
			@RequestBody Rol rolEditar,
			@RequestHeader Map<String, String> header) {
		System.out.println("Cabezera, autorization: " + header );
		System.out.println("Editar el permiso con ID: " + codigo);
		System.out.println("Autor Data: " + rolEditar);
		rolEditar.setId(codigo);
	    
	    return rolEditar;
	}

	@DeleteMapping(path ="delete/{codigo}")
	public void deleteRol(
			@PathVariable Integer codigo,//para indicar que vas a enviar ese dato en la url
			@RequestHeader Map<String, String> header) {
		System.out.println("Cabezera, autorization: " + header );
		System.out.println("eliminar el rol con ID: " + codigo);

	}*/
}
