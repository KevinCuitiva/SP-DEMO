package edu.eci.dosw.segundo_parcial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Modelo de entrada para actualizar datos de perfil del usuario.
 */
public class UserProfileRequest {

    @NotBlank
    private String name;

    @Pattern(regexp = "^$|^\\+?[0-9]{7,15}$", message = "El telefono debe tener entre 7 y 15 digitos")
    private String phoneNumber;

    public UserProfileRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
