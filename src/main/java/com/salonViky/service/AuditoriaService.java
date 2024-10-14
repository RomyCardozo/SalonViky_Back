package com.salonViky.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salonViky.model.Auditoria;
import com.salonViky.model.Usuario;
import com.salonViky.repository.AuditoriaRepository;

@Service
public class AuditoriaService {

	@Autowired
    private AuditoriaRepository auditoriaRepository;

    public void registrarAuditoria(String usuario, String operacion, String tabla, String descripcion) {
        Auditoria auditoria = new Auditoria();
        auditoria.setUsuario(usuario);
        auditoria.setTabla(tabla);
        auditoria.setOperacion(operacion);
        auditoria.setDescripcion(descripcion);
        auditoria.setFecha(LocalDateTime.now());

        auditoriaRepository.save(auditoria);
    }
}
