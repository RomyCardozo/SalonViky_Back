package com.salonViky.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.salonViky.model.Ventas;
import com.salonViky.service.VentaDetalleService;


@RestController
@RequestMapping("ventaDetalle")
public class VentaDetalleController {
	
	@Autowired
	VentaDetalleService ventaDetalleService;
	@GetMapping("listar")
	public List<VentaDetalle> listarVentaDetalle(){
		return new ArrayList<VentaDetalle>();
	}

	@GetMapping("/listard/{id}")
    public ResponseEntity<Map<String, Object>> obtenerDetallesPorVenta(@PathVariable("id") Integer id) {
        List<VentaDetalle> detalles = ventaDetalleService.getVentaDetallesPorVentaId(id);

        if (detalles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Accede a la primera venta (asumiendo que todos los detalles pertenecen a la misma venta)
        Ventas venta = detalles.get(0).getVentas();

        // Prepara la respuesta con los detalles de la venta y el cliente
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("detalles", detalles);
        response.put("clienteNombre", venta.getCliente().getNombre());
        response.put("fechaVenta", venta.getFecha());

        return ResponseEntity.ok(response);
    }
	
}
