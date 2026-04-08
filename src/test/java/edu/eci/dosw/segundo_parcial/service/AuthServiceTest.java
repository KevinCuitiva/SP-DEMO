package edu.eci.dosw.segundo_parcial.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.eci.dosw.segundo_parcial.dto.AuthResponse;
import edu.eci.dosw.segundo_parcial.dto.LoginRequest;
import edu.eci.dosw.segundo_parcial.entity.Role;
import edu.eci.dosw.segundo_parcial.entity.UserEntity;
import edu.eci.dosw.segundo_parcial.exception.InvalidCredentialsException;
import edu.eci.dosw.segundo_parcial.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthService authService;

    // Comprueba que el login devuelve token cuando la contrasena coincide.
    @Test
    void loginReturnsTokenWhenPasswordMatches() {
        UserEntity user = new UserEntity("Admin", "admin@example.com", "encoded", Role.ADMIN);
        user.setId(7L);

        LoginRequest request = new LoginRequest();
        request.setEmail("admin@example.com");
        request.setPassword("secret");

        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("secret", "encoded")).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("token-123");

        AuthResponse response = authService.login(request);

        assertEquals("token-123", response.getToken());
        assertEquals("admin@example.com", response.getUser().getEmail());
    }

    // Comprueba que el login falla cuando la contrasena es incorrecta.
    @Test
    void loginThrowsWhenPasswordIsWrong() {
        UserEntity user = new UserEntity("Admin", "admin@example.com", "encoded", Role.ADMIN);
        LoginRequest request = new LoginRequest();
        request.setEmail("admin@example.com");
        request.setPassword("wrong");

        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);

        var exception = assertThrows(InvalidCredentialsException.class, () -> authService.login(request));
        assertEquals("Correo o contrasena invalidos", exception.getMessage());
    }

    // Comprueba que el registro usa el servicio de usuarios.
    @Test
    void registerDelegatesToUserService() {
        when(userService.createUser(any())).thenReturn(new edu.eci.dosw.segundo_parcial.dto.UserResponse());

        authService.register(new edu.eci.dosw.segundo_parcial.dto.UserRequest());

        verify(userService).createUser(any());
    }
}
