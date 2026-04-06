package edu.eci.dosw.segundo_parcial.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidad que representa una categoría de productos.
 *
 * Una categoría es un clasificador reutilizable para agrupar productos similares.
 * ¿Qué cambiar?:
 * - Si necesitas más atributos (ej: descripción, imagen), agrega columnas aquí
 * - Si necesitas subcategorías, agrega una relación @ManyToOne con otra CategoryEntity
 * - Los restricciones de validación están en el DTO correspondiente (CategoryRequest)
 */
@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {

    // Nombre de la categoría. Debe ser único y no nulo.
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    // Descripción opcional de qué tipos de productos contiene esta categoría.
    @Column(length = 500)
    private String description;

    // Indica si la categoría está activa y disponible para usar.
    @Column(nullable = false)
    private boolean active = true;

    // Constructor sin argumentos requerido por JPA.
    public CategoryEntity() {
    }

    // Constructor con parámetros para crear una categoría rápidamente.
    public CategoryEntity(String name, String description) {
        this.name = name;
        this.description = description;
        this.active = true;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
