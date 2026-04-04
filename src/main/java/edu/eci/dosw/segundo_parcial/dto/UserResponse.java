package edu.eci.dosw.segundo_parcial.dto;

import edu.eci.dosw.segundo_parcial.entity.Role;

/**
 * Modelo de salida que ve el cliente.
 *
 * Aqui no exponemos la contrasena ni campos internos sensibles.
 */
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Role role;

    public UserResponse() {
    }

    // Construye la respuesta con los datos publicos del usuario.
    public UserResponse(Long id, String name, String email, Role role) {
        this(id, name, email, null, role);
    }

    public UserResponse(Long id, String name, String email, String phoneNumber, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
