package edu.eci.dosw.segundo_parcial.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para crear o actualizar un producto.
 *
 * ¿Qué cambiar?:
 * - Si agregas más campos a ProductEntity, agrega propiedades aquí con sus validaciones
 * - categoryId es obligatorio para crear productos; sin categoría no tiene clasificación
 * - creatorId es asignado automáticamente por el servidor (el usuario autenticado)
 */
public class ProductRequest {

    // Nombre único del producto. No puede estar vacío.
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Size(min = 3, max = 200, message = "El nombre debe tener entre 3 y 200 caracteres")
    private String name;

    // Descripción detallada. Es importante para que el cliente entienda qué compra.
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 10, max = 2000, message = "La descripción debe tener entre 10 y 2000 caracteres")
    private String description;

    // Precio en la moneda base. Debe ser positivo y no puede ser null.
    @NotNull(message = "El precio es requerido")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double price;

    // Stock disponible del producto.
    @NotNull(message = "El stock es requerido")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    // ID de la categoría a la que pertenece el producto.
    // El servidor valida que la categoría exista.
    @NotNull(message = "La categoría es requerida")
    private Long categoryId;

    // Constructor sin argumentos requerido para deserialization.
    public ProductRequest() {
    }

    // Constructor con parámetros principales.
    public ProductRequest(String name, String description, Double price, Integer stock, Long categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
