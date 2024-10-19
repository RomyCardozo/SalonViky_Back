package com.salonViky.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salonViky.model.Ventas;
import com.salonViky.service.ServicioService;
import com.salonViky.service.VentasService;

@RestController
@RequestMapping("/informes")
public class InformesController {
	  @Autowired
	    private VentasService ventasService;

	    @Autowired
	    private ServicioService serviciosService;

	    @GetMapping("/ventas")
	    public List<Ventas> obtenerVentas(@RequestParam String startDate, @RequestParam String endDate) {
	        LocalDateTime start = LocalDateTime.parse(startDate);
	        LocalDateTime end = LocalDateTime.parse(endDate);
	        return ventasService.obtenerVentasPorFechas(start, end);
	    }

	/*    @GetMapping("/servicios")
	    public List<Object[]> obtenerServicios(@RequestParam String startDate, @RequestParam String endDate) {
	        LocalDateTime start = LocalDateTime.parse(startDate);
	        LocalDateTime end = LocalDateTime.parse(endDate);
	        return serviciosService.obtenerResumenServicios(start, end);
	    }*/
	    
	    @GetMapping("/servicios")
	    public List<Map<String, Object>> obtenerServicios(@RequestParam String startDate, @RequestParam String endDate) {
	        LocalDateTime start = LocalDateTime.parse(startDate);
	        LocalDateTime end = LocalDateTime.parse(endDate);
	        
	        // Obtener los resultados en formato de array de objetos
	        List<Object[]> resultados = serviciosService.obtenerResumenServicios(start, end);
	        
	        // Crear una lista de mapas para almacenar el resumen de servicios
	        List<Map<String, Object>> resumenServicios = new ArrayList<>();
	        
	        for (Object[] result : resultados) {
	            Map<String, Object> servicioResumen = new HashMap<>();
	            servicioResumen.put("nombre", result[0]); // Nombre del servicio
	            servicioResumen.put("cantidad", result[1]); // Cantidad
	            servicioResumen.put("total", result[2]); // Total recaudado
	            
	            resumenServicios.add(servicioResumen);
	        }

	        return resumenServicios;
	    }

}
