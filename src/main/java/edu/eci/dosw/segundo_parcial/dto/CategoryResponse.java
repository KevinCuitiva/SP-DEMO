package edu.eci.dosw.segundo_parcial.dto;

import java.time.LocalDateTime;

/**
 * DTO para responder con datos de una categoría.
 *
 * Se usa en endpoints GET para no exponer la entidad completa.
 * Incluye información de auditoría (createdAt, updatedAt) para transparencia.
 * ¿Qué cambiar?:
 * - Si agregas campos a CategoryEntity, agrega también aquí para que los clientes puedan verlos
 * - Los getters/setters son obligatorios para serialization a JSON
 */
public class CategoryResponse {

    private Long id;
    private String name;
    private String description;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor sin argumentos requerido para deserialization.
    public CategoryResponse() {
    }

    // Constructor con todos los parámetros para conversión fácil desde CategoryEntity.
    public CategoryResponse(Long id, String name, String description, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
