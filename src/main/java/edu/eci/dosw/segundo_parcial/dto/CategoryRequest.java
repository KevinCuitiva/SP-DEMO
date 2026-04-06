package edu.eci.dosw.segundo_parcial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para crear o actualizar una categoría.
 *
 * Los campos están validados usando Jakarta Validation.
 * ¿Qué cambiar?:
 * - Si agregas más campos a CategoryEntity, agrega también las propiedades aquí con validaciones
 * - Las validaciones deben ser coherentes con las restricciones de la BD
 */
public class CategoryRequest {

    // Nombre de la categoría. No puede estar vacío y debe tener entre 1 y 100 caracteres.
    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String name;

    // Descripción opcional de la categoría. Si se proporciona, máximo 500 caracteres.
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String description;

    // Constructor sin argumentos requerido para deserialization.
    public CategoryRequest() {
    }

    // Constructor con parámetros para tests o creación rápida.
    public CategoryRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
