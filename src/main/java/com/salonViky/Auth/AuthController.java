package com.salonViky.Auth;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.salonViky.controller.UsuarioController;
import com.salonViky.model.Usuario;
import com.salonViky.service.UsuarioService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UsuarioController usuarioController; // Inyectar el controlador de usuario
    
    @Autowired
    private UsuarioService usuarioService;
    
    
//obtiene nombre de usuario autenticado
    @GetMapping("/currentUser")
    public ResponseEntity<Usuario> getCurrentUser(Principal principal) {
  //  	System.out.println("Tipo de principal: " + principal.getClass().getName());
        String nombreUsuario = principal.getName(); // Obtiene el nombre de usuario desde el token
        Optional<Usuario> usuarioOptional = usuarioService.listarPorNombre(nombreUsuario);
        
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
   
    //refresca el token
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || !jwtUtil.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }

        String username = jwtUtil.extractUsername(refreshToken);

        // Verificar que el usuario esté activo y no haya sido eliminado
        Usuario usuario = usuarioService.listarPorNombre(username).orElse(null);
        if (usuario == null || !usuario.getEstado().equals("Activo")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario inactivo o no encontrado");
        }

        String newAccessToken = jwtUtil.generateToken(username);
        String newRefreshToken = jwtUtil.generateRefreshToken(username);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);
        tokens.put("refreshToken", newRefreshToken);

        return ResponseEntity.ok(tokens);
    }
    
    
    
    
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
    	 System.err.println("Intentando autenticar usuario: {}" + authRequest.getUsername());
    	 System.err.println("Intentando autenticar contrasena: {}" + authRequest.getPassword());
        try {
            // Autenticando el usuario con nombre de usuario y contraseña
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
                    );
            System.err.println("Autenticación exitosa para: {}" + authRequest.getUsername());

            // Cargando los detalles del usuario y generando el token JWT
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            final String token = jwtUtil.generateToken(userDetails.getUsername());
            System.err.println("Generando token para usuario: {}"+ userDetails.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
        	System.err.println("Credenciales inválidas para usuario: {}" + authRequest.getUsername() + e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    // Método de registro
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        // Cifrar la contraseña antes de guardar
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));

        // Llamar al método guardar del controlador de usuario
        return usuarioController.guardarUsuario(usuario);
    }
}

// Clase para capturar el cuerpo de la solicitud (username y password)
class AuthRequest {
    private String username;
    private String password;

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

// Clase para enviar la respuesta con el token JWT
class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String gettoken() {
        return token;
    }
}
