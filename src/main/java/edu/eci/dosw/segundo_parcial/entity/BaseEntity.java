package edu.eci.dosw.segundo_parcial.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

/**
 * Clase base para todas las entidades.
 *
 * Proporciona campos comunes y auditoria automática de fechas.
 * ¿Qué cambiar?: Si necesitas cambiar los nombres de columnas o agregar más campos de auditoría
 * (como usuario que actualizó), modifica esta clase y todas las demás entidades las heredarán.
 */
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fecha de creación del registro. Se establece automáticamente en @PrePersist.
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Fecha de última actualización. Se actualiza automáticamente en @PreUpdate.
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Se ejecuta automáticamente antes de guardar por primera vez la entidad.
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Se ejecuta automáticamente antes de actualizar la entidad.
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
