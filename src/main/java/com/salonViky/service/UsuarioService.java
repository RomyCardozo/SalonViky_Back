package com.salonViky.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.salonViky.model.Usuario;
import com.salonViky.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	AuditoriaService auditoriaService;

	public List<Usuario> listar() {
		List<Usuario> result = new ArrayList<Usuario>();
		usuarioRepository.findAll().forEach(result::add);
		;// retorna un iterable de user, debeemos cambiar pq no sirve para web service//
			// convert iterable collection java
		return result;
	}

	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioRepository.findAll();
	}

	public List<Usuario> listarPorNombrePaginacion(String nombre, Pageable pageable) {
		return usuarioRepository.findByNombreLikeIgnoreCase(nombre, pageable);
	}

	public Optional<Usuario> listarPorNombre(String nombre) {
		return usuarioRepository.findByNombre(nombre);
	}

	// buscar id
	public Optional<Usuario> findById(Integer id) {
		return usuarioRepository.findById(id);
	}

	public Usuario guardar(Usuario usuario) {
		// System.out.println("Datos del usuario recibidos: " + usuario);
		Optional<Usuario> usuarioExistente = usuarioRepository.findByNombre(usuario.getNombre());

		if (usuarioExistente.isPresent() && usuarioExistente.get().getEstado().equalsIgnoreCase("Activo")) {
			throw new IllegalArgumentException("Nombre de usuario ya en uso");
		}

		return usuarioRepository.save(usuario);
	}

	public Usuario actualizar(Usuario usuario) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		usuario.setClave(passwordEncoder.encode(usuario.getClave()));
		return usuarioRepository.save(usuario);
	}

	// eliminar por id
	public void deleteById(Integer id) {
		usuarioRepository.deleteById(id);
	}

	// Método adicional para cargar el usuario por nombre de usuario para Spring
	// Security
	@Override
	public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByNombre(nombre)

				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + nombre));

		return usuario;
	}

	public Usuario eliminarActivo(Usuario usuario) {

		return usuarioRepository.save(usuario);

	}

}
