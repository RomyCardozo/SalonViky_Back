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

	/*@PostMapping(path ="save")//preguntar si venta detalle va a tener este metodo 
	public VentaDetalle crearVentaDetalle( @RequestBody VentaDetalle ventasDetalleRegistrar){
		System.out.println("Registrar venta" + ventasDetalleRegistrar);
		//realizar una operacion de insert en la bd
		return ventasDetalleRegistrar;
	}*/
	/*@PutMapping(path ="update/{codigo}")
	public VentaDetalle modificarVentas(
			@PathVariable Integer codigo,//para indicar que vas a enviar ese dato en la url
			@RequestBody VentaDetalle ventasDetalleModificar,
			@RequestHeader Map<String, String> header) {
		System.out.println("Cabezera, autorization: " + header );
		System.out.println("Modificar la venta con ID: " + codigo);
		System.out.println(" Data: " + ventasDetalleModificar);
		ventasDetalleModificar.setId(codigo);
	    return ventasDetalleModificar;
	}

	@DeleteMapping(path ="delete/{codigo}")
	public void deleteVentasdetalle(
			@PathVariable Integer codigo,//para indicar que vas a enviar ese dato en la url
			@RequestHeader Map<String, String> header) {
		System.out.println("Cabezera, autorization: " + header );
		System.out.println("eliminar la venta detalle con ID: " + codigo);

	}*/
	
}
