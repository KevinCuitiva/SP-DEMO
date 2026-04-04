package edu.eci.dosw.segundo_parcial.dto;

import edu.eci.dosw.segundo_parcial.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Modelo de entrada para crear usuarios.
 *
 * Si agregas mas campos del usuario, este DTO debe crecer junto con la entidad.
 */
public class UserRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @Pattern(regexp = "^$|^\\+?[0-9]{7,15}$", message = "El telefono debe tener entre 7 y 15 digitos")
    private String phoneNumber;

    private Role role = Role.USER;

    public UserRequest() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
