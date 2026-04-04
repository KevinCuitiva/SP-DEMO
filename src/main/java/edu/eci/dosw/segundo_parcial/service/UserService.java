package edu.eci.dosw.segundo_parcial.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.eci.dosw.segundo_parcial.dto.UserProfileRequest;
import edu.eci.dosw.segundo_parcial.dto.UserRequest;
import edu.eci.dosw.segundo_parcial.dto.UserResponse;
import edu.eci.dosw.segundo_parcial.entity.Role;
import edu.eci.dosw.segundo_parcial.entity.UserEntity;
import edu.eci.dosw.segundo_parcial.exception.ResourceNotFoundException;
import edu.eci.dosw.segundo_parcial.repository.UserRepository;

/**
 * Contiene la logica CRUD de usuarios.
 *
 * Si luego agregas mas campos o relaciones, este servicio es donde centralizas las reglas.
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Devuelve todos los usuarios como DTOs para no exponer la entidad completa.
    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    // Busca un usuario por id.
    public UserResponse findUserById(Long id) {
        return toResponse(findEntityById(id));
    }

    // Crea un usuario, valida que el correo no exista y encripta la contrasena.
    @Transactional
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(normalizeEmail(request.getEmail()))) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }

        UserEntity user = new UserEntity();
        user.setName(request.getName().trim());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(normalizePhone(request.getPhoneNumber()));
        user.setRole(request.getRole() == null ? Role.USER : request.getRole());

        return toResponse(userRepository.save(user));
    }

    // Actualiza datos de perfil basicos del usuario.
    @Transactional
    public UserResponse updateUserProfile(Long id, UserProfileRequest request) {
        UserEntity user = findEntityById(id);
        user.setName(request.getName().trim());
        user.setPhoneNumber(normalizePhone(request.getPhoneNumber()));
        return toResponse(userRepository.save(user));
    }

    // Elimina un usuario por id.
    @Transactional
    public void deleteUser(Long id) {
        UserEntity user = findEntityById(id);
        userRepository.delete(user);
    }

    // Busca la entidad completa por correo.
    public UserEntity findEntityByEmail(String email) {
        return userRepository.findByEmail(normalizeEmail(email))
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    // Busca la entidad completa por id.
    private UserEntity findEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    // Convierte la entidad a la respuesta publica.
    private UserResponse toResponse(UserEntity user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getPhoneNumber(), user.getRole());
    }

    // Normaliza el correo para evitar espacios y diferencias de mayusculas.
    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }

    private String normalizePhone(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }

        String normalized = phoneNumber.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
