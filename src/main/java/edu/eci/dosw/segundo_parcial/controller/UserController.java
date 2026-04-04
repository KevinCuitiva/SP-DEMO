package edu.eci.dosw.segundo_parcial.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.dosw.segundo_parcial.dto.UserProfileRequest;
import edu.eci.dosw.segundo_parcial.dto.UserRequest;
import edu.eci.dosw.segundo_parcial.dto.UserResponse;
import edu.eci.dosw.segundo_parcial.service.UserService;
import jakarta.validation.Valid;

/**
 * Endpoints basicos para leer y administrar usuarios.
 *
 * Si luego agregas mas entidades, puedes copiar esta misma estructura de controlador.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Devuelve todos los usuarios.
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAllUsers();
    }

    // Busca un usuario por id.
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    // Crea un usuario nuevo.
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    // Actualiza datos de perfil del usuario.
    @PutMapping("/{id}/profile")
    public UserResponse updateUserProfile(@PathVariable Long id, @Valid @RequestBody UserProfileRequest request) {
        return userService.updateUserProfile(id, request);
    }

    // Elimina un usuario por id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
