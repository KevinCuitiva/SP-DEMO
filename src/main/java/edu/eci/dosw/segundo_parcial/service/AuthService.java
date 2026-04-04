package edu.eci.dosw.segundo_parcial.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.eci.dosw.segundo_parcial.dto.AuthResponse;
import edu.eci.dosw.segundo_parcial.dto.LoginRequest;
import edu.eci.dosw.segundo_parcial.dto.UserRequest;
import edu.eci.dosw.segundo_parcial.dto.UserResponse;
import edu.eci.dosw.segundo_parcial.entity.UserEntity;
import edu.eci.dosw.segundo_parcial.exception.InvalidCredentialsException;
import edu.eci.dosw.segundo_parcial.repository.UserRepository;

/**
 * Maneja el login y el registro.
 *
 * Si agregas refresh tokens o recuperacion de contrasena, este servicio es un buen punto de extension.
 */
@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
            UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    // Valida credenciales y devuelve el token JWT.
    public AuthResponse login(LoginRequest request) {
        String email = normalizeEmail(request.getEmail());
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Correo o contrasena invalidos"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Correo o contrasena invalidos");
        }

        UserResponse userResponse = new UserResponse(user.getId(), user.getName(), user.getEmail(),
            user.getPhoneNumber(), user.getRole());
        return new AuthResponse(jwtService.generateToken(user), userResponse);
    }

    // Registra un usuario usando la misma logica del servicio de usuarios.
    @Transactional
    public UserResponse register(UserRequest request) {
        return userService.createUser(request);
    }

    // Normaliza el correo para evitar problemas de mayusculas o espacios.
    private String normalizeEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidCredentialsException("Correo o contrasena invalidos");
        }

        return email.trim().toLowerCase();
    }
}
