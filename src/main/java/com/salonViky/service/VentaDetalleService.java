package com.salonViky.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salonViky.model.VentaDetalle;
import com.salonViky.model.Ventas;
import com.salonViky.repository.VentasDetalleRepository;
import com.salonViky.repository.VentasRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class VentaDetalleService {
	@Autowired
	VentasDetalleRepository ventasDetalleRepository;
	@Autowired
	VentasRepository ventasRepository;

	@Autowired
	private Validator validator;

	public List<VentaDetalle> getVentaDetallesPorVentaId(Integer ventaId) {
		// Buscar la venta usando el ID
		Ventas venta = ventasRepository.findById(ventaId)
				.orElseThrow(() -> new RuntimeException("Venta no encontrada"));

		// Buscar los detalles de la venta
		return ventasDetalleRepository.findByVentas(venta);
	}

	public List<VentaDetalle> listar() {
		List<VentaDetalle> result = new ArrayList<VentaDetalle>();
		ventasDetalleRepository.findAll().forEach(result::add);
		;// retorna un iterable de user, debeemos cambiar pq no sirve para web service//
			// convert iterable collection java
		return result;
	}

	public VentaDetalle guardarDetalle(VentaDetalle ventaDetalle) {
		Set<ConstraintViolation<VentaDetalle>> violations = validator.validate(ventaDetalle);

		if (!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<VentaDetalle> constraintViolation : violations) {
				sb.append(constraintViolation.getMessage());
			}
			throw new RuntimeException("Error de validación: " + sb.toString());
		}

		return ventasDetalleRepository.save(ventaDetalle);
	}

}
