package com.salonViky.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salonViky.model.Servicio;
import com.salonViky.model.Ventas;
import com.salonViky.repository.VentasDetalleRepository;
import com.salonViky.repository.VentasRepository;

@Service
public class VentasService {
	
	@Autowired
	VentasRepository vr;
	
	@Autowired
	private VentasDetalleRepository vdr;
	

	
	public List<Ventas> listar(){
		List<Ventas> result = new ArrayList<Ventas>();
		vr.findAll().forEach(result::add);;//retorna un iterable de user, debeemos cambiar pq no sirve para web service// convert iterable collection java
		return result;
	}
	
	  public List<Ventas> findAll() {
	        return (List<Ventas>) vr.findAll();
	    }
	    
	    public List<Ventas> listarPorFechaPaginacion(LocalDateTime fecha, Pageable pageable) {
	        return vr.findByFechaLikeIgnoreCase(fecha, pageable);
	    }	
		
	    //este se utiliza para registrar y modificar
		public Ventas guardar(Ventas ventas) {
			return vr.save(ventas);
		}

	    //buscar id
	    public Optional<Ventas> findById(Integer id) {
	        return vr.findById(id);
	    }

	    //eliminar por id
	    public void deleteById(Integer id) {
	        vr.deleteById(id);
	    }

	    	
	/* public double calcularTotalVenta(Integer id) {
    Optional<VentaDetalle> detalles = vdr.findById(id);
    return detalles.stream().mapToDouble(VentaDetalle::getSubTotal).sum();
}*/
	
	/*
	public List<Ventas> obtenerPorFiltroYPaginacion(
			String filtro, Integer pagina,
			Integer tamano){
		List<Ventas> ventas = vr.byFilterPagination
				(filtro, pagina, tamano);
		//agregar o hacer mas cosas con clientes
		return ventas;
	}
	
	
	public Ventas insertOrUpdate(Ventas ventas) {
		//Validacion
		if (!(ventas.getEstado() != null && ventas.getEstado().length() > 3)) {
			throw new RuntimeException("Error en Nombre de Autor");
		}
		Ventas ventasBuscar = vr.findById(ventas.getId());
		return ventasBuscar;
	}
*/
}
