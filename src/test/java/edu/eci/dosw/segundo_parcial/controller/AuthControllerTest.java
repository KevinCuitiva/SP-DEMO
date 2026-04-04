package edu.eci.dosw.segundo_parcial.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.eci.dosw.segundo_parcial.dto.AuthResponse;
import edu.eci.dosw.segundo_parcial.dto.LoginRequest;
import edu.eci.dosw.segundo_parcial.dto.UserRequest;
import edu.eci.dosw.segundo_parcial.dto.UserResponse;
import edu.eci.dosw.segundo_parcial.entity.Role;
import edu.eci.dosw.segundo_parcial.service.AuthService;

class AuthControllerTest {

    @Test
    void loginDelegatesToService() {
        AuthService authService = Mockito.mock(AuthService.class);
        AuthController controller = new AuthController(authService);
        LoginRequest request = new LoginRequest();
        request.setEmail("admin@example.com");
        request.setPassword("secret");
        when(authService.login(request)).thenReturn(new AuthResponse("token", new UserResponse(1L, "Admin", "admin@example.com", Role.ADMIN)));

        AuthResponse response = controller.login(request);

        assertEquals("token", response.getToken());
        verify(authService).login(request);
    }

    @Test
    void registerDelegatesToService() {
        AuthService authService = Mockito.mock(AuthService.class);
        AuthController controller = new AuthController(authService);
        UserRequest request = new UserRequest();
        request.setName("User");
        request.setEmail("user@example.com");
        request.setPassword("secret");
        when(authService.register(request)).thenReturn(new UserResponse(2L, "User", "user@example.com", Role.USER));

        UserResponse response = controller.register(request).getBody();

        assertEquals(2L, response.getId());
        verify(authService).register(request);
    }
}
