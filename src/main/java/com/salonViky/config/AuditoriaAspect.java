package com.salonViky.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.salonViky.service.AuditoriaService;

@Aspect
@Component
public class AuditoriaAspect {

	@Autowired
	private AuditoriaService auditoriaService;
	
	private static final Logger logger = LoggerFactory.getLogger(AuditoriaAspect.class);

	//-------------- tabla servicio 
	// Punto de corte para operaciones CREATE
	@Pointcut("execution(* com.salonViky.service.*.guardar(..))")
	public void guardarServicePointcut() {
	}

	// Punto de corte para operaciones DELETE
	@Pointcut("execution(* com.salonViky.service.*.eliminarActivo(..))")
	public void eliminarServicioPointcut() {
	}

	// Punto de corte para operaciones UPDATE
	@Pointcut("execution(* com.salonViky.service.*.actualizar(..))")
	public void editarServicePointcut() {
	}

	
	@AfterReturning("guardarServicePointcut()")
	public void registrarCreacionServicio(JoinPoint joinPoint) {
		Object entidad = joinPoint.getArgs()[0];
		String usuario = obtenerUsuarioActual();
		logger.info("Creación detectada en tabla: {}, por el usuario: {}", entidad.getClass().getSimpleName(), usuario);
		auditoriaService.registrarAuditoria(usuario, "INSERT", entidad.getClass().getSimpleName(), "Registro creado");
	}
	
	@AfterReturning("eliminarServicioPointcut()")
	public void registrarEliminacionServicio(JoinPoint joinPoint) {
		Object entidad = joinPoint.getArgs()[0];
		String usuario = obtenerUsuarioActual();
		logger.info("Eliminacion detectada en tabla: {}, por el usuario: {}", identificarEntidad(joinPoint.getTarget().getClass().getSimpleName()), usuario);
		auditoriaService.registrarAuditoria(usuario, "DELETE", entidad.getClass().getSimpleName(),
				"Registro eliminado");
	}
	

	@AfterReturning("editarServicePointcut()")
	public void editarCreacionServicio(JoinPoint joinPoint) {
		Object entidad = joinPoint.getArgs()[0];
		String usuario = obtenerUsuarioActual();
		logger.info("Actualizacion detectada en tabla: {}, por el usuario: {}", entidad.getClass().getSimpleName(), usuario);
		auditoriaService.registrarAuditoria(usuario, "UPDATE", entidad.getClass().getSimpleName(),
				"Registro actualizado");
	}
	


	private String obtenerUsuarioActual() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		} else {
			return principal.toString();
		} 
	}
	
	private String identificarEntidad(String claseServicio) {
	    switch (claseServicio) {
	        case "ClienteService":
	            return "Cliente";
	        case "UsuarioService":
	            return "Usuario";
	        case "ServicioService":
	            return "Servicio";
	        case "VentasService":
	            return "Ventas";
	        default:
	            return "Entidad Desconocida"; // Opcionalmente maneja casos desconocidos
	    }
	}
	

}