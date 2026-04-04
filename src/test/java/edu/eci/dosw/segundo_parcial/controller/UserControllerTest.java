package edu.eci.dosw.segundo_parcial.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.eci.dosw.segundo_parcial.dto.UserProfileRequest;
import edu.eci.dosw.segundo_parcial.dto.UserRequest;
import edu.eci.dosw.segundo_parcial.dto.UserResponse;
import edu.eci.dosw.segundo_parcial.entity.Role;
import edu.eci.dosw.segundo_parcial.service.UserService;

class UserControllerTest {

    @Test
    void getAllUsersDelegatesToService() {
        UserService userService = Mockito.mock(UserService.class);
        UserController controller = new UserController(userService);
        when(userService.findAllUsers()).thenReturn(List.of(new UserResponse(1L, "Demo", "demo@example.com", Role.USER)));

        List<UserResponse> responses = controller.getAllUsers();

        assertEquals(1, responses.size());
        verify(userService).findAllUsers();
    }

    @Test
    void createUserReturnsCreatedResponse() {
        UserService userService = Mockito.mock(UserService.class);
        UserController controller = new UserController(userService);
        UserRequest request = new UserRequest();
        request.setName("Nuevo");
        request.setEmail("nuevo@example.com");
        request.setPassword("secret");
        when(userService.createUser(request)).thenReturn(new UserResponse(5L, "Nuevo", "nuevo@example.com", Role.USER));

        UserResponse response = controller.createUser(request).getBody();

        assertEquals(5L, response.getId());
    }

    @Test
    void updateUserProfileDelegatesToService() {
        UserService userService = Mockito.mock(UserService.class);
        UserController controller = new UserController(userService);
        UserProfileRequest request = new UserProfileRequest();
        request.setName("Nuevo Nombre");
        request.setPhoneNumber("+573001112233");

        when(userService.updateUserProfile(5L, request))
                .thenReturn(new UserResponse(5L, "Nuevo Nombre", "nuevo@example.com", "+573001112233", Role.USER));

        UserResponse response = controller.updateUserProfile(5L, request);

        assertEquals("+573001112233", response.getPhoneNumber());
        verify(userService).updateUserProfile(5L, request);
    }
}
