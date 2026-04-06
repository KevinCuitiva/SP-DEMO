package edu.eci.dosw.segundo_parcial.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad que representa un producto.
 *
 * Un producto es un artículo que pueden crear/administrar los usuarios y que pertenece a una categoría.
 * ¿Qué cambiar?:
 * - Si necesitas más atributos (ej: imagen, color, tamaño), agrega columnas aquí
 * - Si necesitas múltiples categorías por producto, cambia a @ManyToMany
 * - Si necesitas inventario avanzado, crea una entidad InventoryEntity separada
 * - El usuario "creador" representa quién publica el producto; si es una tienda, cambia el nombre a "seller" o "vendor"
 */
@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity {

    // Nombre del producto. Debe ser único y descriptivo.
    @Column(nullable = false, unique = true, length = 200)
    private String name;

    // Descripción detallada del producto. Puede incluir especificaciones, materiales, etc.
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    // Precio unitario del producto en la moneda base (ej: USD, EUR). No puede ser negativo.
    @Column(nullable = false)
    private Double price;

    // Cantidad disponible en el inventario. Si es 0, el producto está descontinuado.
    @Column(nullable = false)
    private Integer stock = 0;

    // Referencia a la categoría del producto. Un producto puede tener una sola categoría.
    // ForeignKey especifica el nombre de la clave foránea en la BD.
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_category"))
    private CategoryEntity category;

    // Referencia al usuario que crea/publica el producto (vendedor/creador).
    // Cada producto debe estar asociado a un usuario.
    @ManyToOne(optional = false)
    @JoinColumn(name = "creator_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_creator"))
    private UserEntity creator;

    // Indica si el producto está disponible para compra. Un admin puede desactivar productos sin eliminarlos.
    @Column(nullable = false)
    private boolean available = true;

    // Rating promedio del producto (0.0 a 5.0). Se calcula en base a reseñas.
    @Column(name = "rating", columnDefinition = "DECIMAL(3,2)")
    private Double rating = 0.0;

    // Constructor sin argumentos requerido por JPA.
    public ProductEntity() {
    }

    // Constructor con parámetros principales.
    public ProductEntity(String name, String description, Double price, Integer stock, CategoryEntity category, UserEntity creator) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.creator = creator;
        this.available = true;
        this.rating = 0.0;
    }

    // Método helper: Reduce el stock cuando se realiza una compra.
    // ¿Qué cambiar?: Si necesitas lógica de compra más compleja (ej: reservas, backorders),
    // modifica este método o crea uno nuevo adicional.
    public void reduceStock(Integer quantity) {
        if (this.stock < quantity) {
            throw new IllegalArgumentException("Stock insuficiente para esta operación");
        }
        this.stock -= quantity;
    }

    // Método helper: Aumenta el stock (para devoluciones o reabastecimiento).
    public void increaseStock(Integer quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        }
        this.stock += quantity;
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

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
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
}
