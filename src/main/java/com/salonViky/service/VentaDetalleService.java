package com.salonViky.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salonViky.model.VentaDetalle;
import com.salonViky.repository.VentasDetalleRepository;

@Service
public class VentaDetalleService {
	@Autowired
	VentasDetalleRepository vdr;
	
	public List<VentaDetalle> listar(){
		List<VentaDetalle> result = new ArrayList<VentaDetalle>();
		vdr.findAll().forEach(result::add);;//retorna un iterable de user, debeemos cambiar pq no sirve para web service// convert iterable collection java
		return result;
	}
	
}
