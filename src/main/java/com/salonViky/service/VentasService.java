package com.salonViky.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.salonViky.model.Ventas;
import com.salonViky.repository.VentasRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class VentasService {

	@Autowired
	VentasRepository vr;

	@Autowired
	Validator validator;

	public List<Ventas> listar() {
		List<Ventas> result = new ArrayList<Ventas>();
		vr.findAll().forEach(result::add);
		;// retorna un iterable de user, debeemos cambiar pq no sirve para web service//
			// convert iterable collection java
		return result;
	}

	public List<Ventas> findAll() {
		return (List<Ventas>) vr.findAll();
	}

	// este se utiliza para registrar y modificar
	public Ventas guardar(Ventas ventas) {
		Set<ConstraintViolation<Ventas>> violations = validator.validate(ventas);

		String errorValidation = "";
		for (ConstraintViolation<Ventas> cv : violations) {
			System.out.println("Procesando propiedad: " + cv.getPropertyPath());
			System.out.println("Mensaje de error: " + cv.getMessage());
			errorValidation += " Error " + cv.getPropertyPath() + " " + cv.getMessage();
		}

		if (!violations.isEmpty()) {

			throw new RuntimeException(errorValidation);
		}

		return vr.save(ventas);
	}

	public Ventas actualizar(Ventas ventas) {
		// Calcular el total usando el método existente
		//ventas.calcularTotal();

		// Realizar la validación
		Set<ConstraintViolation<Ventas>> violations = validator.validate(ventas);

		if (!violations.isEmpty()) {
			StringBuilder errorMessage = new StringBuilder();
			for (ConstraintViolation<Ventas> violation : violations) {
				errorMessage.append("Error en ").append(violation.getPropertyPath()).append(": ")
						.append(violation.getMessage()).append(". ");
			}
			throw new RuntimeException(errorMessage.toString());
		}

		return vr.save(ventas);
	}

	public Ventas eliminarActivo(Ventas ventas) {

		return vr.save(ventas);
	}

	// buscar id
	public Optional<Ventas> findById(Integer id) {
		return vr.findById(id);
	}

	// eliminar por id
	public void deleteById(Integer id) {
		vr.deleteById(id);
	}

	public List<Ventas> obtenerVentasPorFechas(LocalDateTime startDate, LocalDateTime endDate) {
        return vr.findByFechaBetween(startDate, endDate);
    }
}
