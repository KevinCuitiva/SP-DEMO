package edu.eci.dosw.segundo_parcial.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.eci.dosw.segundo_parcial.dto.ProductRequest;
import edu.eci.dosw.segundo_parcial.dto.ProductResponse;
import edu.eci.dosw.segundo_parcial.entity.CategoryEntity;
import edu.eci.dosw.segundo_parcial.entity.ProductEntity;
import edu.eci.dosw.segundo_parcial.entity.UserEntity;
import edu.eci.dosw.segundo_parcial.exception.ResourceNotFoundException;
import edu.eci.dosw.segundo_parcial.repository.CategoryRepository;
import edu.eci.dosw.segundo_parcial.repository.ProductRepository;

/**
 * Servicio para operaciones CRUD de productos.
 *
 * Centraliza la lógica de negocio para productos, incluyendo relaciones con categorías y usuarios.
 * ¿Qué cambiar?:
 * - Si necesitas lógica de inventario avanzada, agrega métodos aquí
 * - Si necesitas búsquedas complejas, agrega métodos de búsqueda aquí
 * - Para cálculo de ratings, agrega lógica en integración con un servicio de reseñas
 */
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, 
            CategoryService categoryService, UserService userService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    // Obtiene todos los productos disponibles.
    public List<ProductResponse> findAllAvailableProducts() {
        return productRepository.findByAvailable(true)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Obtiene todos los productos (incluyendo no disponibles).
    // Para administradores.
    public List<ProductResponse> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Obtiene un producto específico por ID.
    public ProductResponse findProductById(Long id) {
        return toResponse(findEntityById(id));
    }

    // Obtiene todos los productos de una categoría.
    public List<ProductResponse> findProductsByCategory(Long categoryId) {
        // Valida que la categoría existe
        categoryService.findCategoryById(categoryId);
        
        return productRepository.findByCategoryIdAndAvailable(categoryId, true)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Obtiene todos los productos creados/vendidos por un usuario específico.
    public List<ProductResponse> findProductsByCreator(Long creatorId) {
        // Valida que el usuario existe
        userService.findUserById(creatorId);
        
        return productRepository.findByCreator_Id(creatorId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Busca productos por nombre.
    public List<ProductResponse> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .filter(ProductEntity::isAvailable)
                .map(this::toResponse)
                .toList();
    }

    // Crea un nuevo producto.
    // El usuario autenticado es el creador del producto.
    @Transactional
    public ProductResponse createProduct(ProductRequest request, Long creatorId) {
        // Validar que la categoría existe
        var categoryResponse = categoryService.findCategoryById(request.getCategoryId());
        CategoryEntity category = findCategoryEntityById(categoryResponse.getId());

        // Validar que el usuario (creator) existe
        UserEntity creator = userService.findEntityByEmail(userService.findUserById(creatorId).getEmail());

        // Validar que el precio es positivo
        if (request.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        // Validar que el stock no es negativo
        if (request.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

        ProductEntity product = new ProductEntity();
        product.setName(request.getName().trim());
        product.setDescription(request.getDescription().trim());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(category);
        product.setCreator(creator);
        product.setAvailable(true);
        product.setRating(0.0);

        return toResponse(productRepository.save(product));
    }

    // Actualiza un producto existente.
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        ProductEntity product = findEntityById(id);

        // Validar categoría si cambió
        if (!product.getCategory().getId().equals(request.getCategoryId())) {
            var categoryResponse = categoryService.findCategoryById(request.getCategoryId());
            product.setCategory(findCategoryEntityById(categoryResponse.getId()));
        }

        // Validar precio
        if (request.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        product.setName(request.getName().trim());
        product.setDescription(request.getDescription().trim());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        return toResponse(productRepository.save(product));
    }

    // Reduce el stock cuando se realiza una compra.
    // ¿Qué cambiar?: Si necesitas lógica de reservas o validaciones adicionales, modifica aquí.
    @Transactional
    public void reduceStock(Long productId, Integer quantity) {
        ProductEntity product = findEntityById(productId);
        product.reduceStock(quantity);
        productRepository.save(product);
    }

    // Aumenta el stock (para devoluciones o reabastecimiento).
    @Transactional
    public void increaseStock(Long productId, Integer quantity) {
        ProductEntity product = findEntityById(productId);
        product.increaseStock(quantity);
        productRepository.save(product);
    }

    // Desactiva un producto (soft delete).
    // Mejor que eliminar porque preserva el historial.
    @Transactional
    public ProductResponse deactivateProduct(Long id) {
        ProductEntity product = findEntityById(id);
        product.setAvailable(false);
        return toResponse(productRepository.save(product));
    }

    // Elimina completamente un producto.
    @Transactional
    public void deleteProduct(Long id) {
        ProductEntity product = findEntityById(id);
        productRepository.delete(product);
    }

    // Obtiene la entidad de producto para uso interno.
    private ProductEntity findEntityById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
    }

    // Obtiene la entidad de categoría para uso interno.
    // Solo acepta un ID que ya fue validado.
    private CategoryEntity findCategoryEntityById(Long id) {
        // Esto es un helper que asume que la categoría fue validada antes
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
    }

    // Convierte una entidad de producto a su DTO de respuesta.
    // Incluye información anidada de categoría y creador.
    private ProductResponse toResponse(ProductEntity product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCreator().getId(),
                product.getCreator().getName(),
                product.isAvailable(),
                product.getRating(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
