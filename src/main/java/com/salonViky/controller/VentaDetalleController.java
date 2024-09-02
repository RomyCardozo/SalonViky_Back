package com.salonViky.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salonViky.model.VentaDetalle;


@RestController
@RequestMapping("ventaDetalle")
public class VentaDetalleController {
	@GetMapping("listar")
	public List<VentaDetalle> listarVentaDetalle(){
		return new ArrayList<VentaDetalle>();
	}

	
}
