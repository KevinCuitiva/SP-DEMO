package edu.eci.dosw.segundo_parcial.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.eci.dosw.segundo_parcial.dto.UserProfileRequest;
import edu.eci.dosw.segundo_parcial.dto.UserRequest;
import edu.eci.dosw.segundo_parcial.dto.UserResponse;
import edu.eci.dosw.segundo_parcial.entity.Role;
import edu.eci.dosw.segundo_parcial.entity.UserEntity;
import edu.eci.dosw.segundo_parcial.exception.ResourceNotFoundException;
import edu.eci.dosw.segundo_parcial.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    // Comprueba que se mapean todas las entidades a DTOs.
    @Test
    void findAllUsersMapsEntities() {
        UserEntity user = new UserEntity("Usuario Demo", "demo@example.com", "encoded", Role.USER);
        user.setId(1L);
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponse> responses = userService.findAllUsers();

        assertEquals(1, responses.size());
        assertEquals("demo@example.com", responses.get(0).getEmail());
    }

    // Comprueba que el servicio responde con error si el usuario no existe.
    @Test
    void findUserByIdThrowsWhenMissing() {
        when(userRepository.findById(5L)).thenReturn(Optional.empty());

        var exception = assertThrows(ResourceNotFoundException.class, () -> userService.findUserById(5L));
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    // Comprueba que la creacion encripta la contrasena y guarda el usuario.
    @Test
    void createUserEncodesPasswordAndSaves() {
        UserRequest request = new UserRequest();
        request.setName("Nuevo");
        request.setEmail("nuevo@example.com");
        request.setPassword("secret");
        request.setPhoneNumber("+573001112233");
        request.setRole(Role.ADMIN);

        when(userRepository.existsByEmail("nuevo@example.com")).thenReturn(false);
        when(passwordEncoder.encode("secret")).thenReturn("encoded-secret");
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity saved = invocation.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        UserResponse response = userService.createUser(request);

        assertEquals(10L, response.getId());
        assertEquals(Role.ADMIN, response.getRole());
        assertEquals("+573001112233", response.getPhoneNumber());
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void updateUserProfileUpdatesNameAndPhone() {
        UserEntity user = new UserEntity("Usuario Demo", "demo@example.com", "encoded", Role.USER);
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserProfileRequest request = new UserProfileRequest();
        request.setName("Nombre Actualizado");
        request.setPhoneNumber("+573224445566");

        UserResponse response = userService.updateUserProfile(1L, request);

        assertEquals("Nombre Actualizado", response.getName());
        assertEquals("+573224445566", response.getPhoneNumber());
    }

    // Comprueba que borrar usa el repositorio.
    @Test
    void deleteUserRemovesExistingUser() {
        UserEntity user = new UserEntity("Usuario Demo", "demo@example.com", "encoded", Role.USER);
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).delete(user);
    }
}
