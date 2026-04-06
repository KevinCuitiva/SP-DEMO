package edu.eci.dosw.segundo_parcial.dto;

import java.time.LocalDateTime;

/**
 * DTO para responder con datos de un producto.
 *
 * Se usa en endpoints GET para no exponer la entidad completa.
 * Incluye información de auditoría y relaciones simplificadas.
 * ¿Qué cambiar?:
 * - Si agregas campos a ProductEntity, agrega también aquí
 * - Los IDs de relaciones (creatorId, categoryId) se incluyen para que el cliente pueda hacer referencias
 * - El rating es calculado automáticamente por el servidor
 */
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Long categoryId;
    private String categoryName;
    private Long creatorId;
    private String creatorName;
    private boolean available;
    private Double rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor sin argumentos requerido para deserialization.
    public ProductResponse() {
    }

    // Constructor con todos los parámetros.
    public ProductResponse(Long id, String name, String description, Double price, Integer stock,
            Long categoryId, String categoryName, Long creatorId, String creatorName,
            boolean available, Double rating, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.available = available;
        this.rating = rating;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
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
