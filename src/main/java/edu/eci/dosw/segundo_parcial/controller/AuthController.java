package edu.eci.dosw.segundo_parcial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.dosw.segundo_parcial.dto.AuthResponse;
import edu.eci.dosw.segundo_parcial.dto.LoginRequest;
import edu.eci.dosw.segundo_parcial.dto.UserRequest;
import edu.eci.dosw.segundo_parcial.dto.UserResponse;
import edu.eci.dosw.segundo_parcial.service.AuthService;
import jakarta.validation.Valid;

/**
 * Endpoints de autenticacion para login y registro.
 *
 * Si quieres agregar recuperacion de contrasena o refresh token, este controlador
 * es el lugar natural para hacerlo.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Inicia sesion y devuelve un token JWT.
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    // Registra un usuario nuevo.
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
