package edu.eci.dosw.segundo_parcial.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.dosw.segundo_parcial.dto.ProductRequest;
import edu.eci.dosw.segundo_parcial.dto.ProductResponse;
import edu.eci.dosw.segundo_parcial.service.ProductService;
import edu.eci.dosw.segundo_parcial.service.UserService;
import jakarta.validation.Valid;

/**
 * Endpoints para gestionar productos.
 *
 * ¿Qué cambiar?:
 * - Si agregas más filtros de búsqueda, agrega parámetros aquí (@RequestParam)
 * - Si necesitas paginación, agrega Pageable de Spring Data
 * - El usuario autenticado se obtiene de Authentication para identificar el creador del producto
 * - Las restricciones de seguridad están en SecurityConfig
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    // GET /api/products
    // Obtiene todos los productos disponibles.
    @GetMapping
    public List<ProductResponse> getAllAvailableProducts() {
        return productService.findAllAvailableProducts();
    }

    // GET /api/products/{id}
    // Obtiene un producto específico por su ID.
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.findProductById(id);
    }

    // GET /api/products/search?name=...
    // Busca productos por nombre (búsqueda parcial, case-insensitive).
    @GetMapping("/search")
    public List<ProductResponse> searchProducts(@RequestParam String name) {
        return productService.searchProductsByName(name);
    }

    // GET /api/products/category/{categoryId}
    // Obtiene todos los productos de una categoría específica.
    @GetMapping("/category/{categoryId}")
    public List<ProductResponse> getProductsByCategory(@PathVariable Long categoryId) {
        return productService.findProductsByCategory(categoryId);
    }

    // GET /api/products/creator/{creatorId}
    // Obtiene todos los productos creados por un usuario específico (vendedor).
    @GetMapping("/creator/{creatorId}")
    public List<ProductResponse> getProductsByCreator(@PathVariable Long creatorId) {
        return productService.findProductsByCreator(creatorId);
    }

    // POST /api/products
    // Crea un nuevo producto.
    // El usuario autenticado es el creador del producto.
    // Solo usuarios autenticados pueden crear productos (validado en SecurityConfig).
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest request,
            Authentication authentication) {
        // Obtiene el email del usuario autenticado desde el token JWT.
        String userEmail = authentication.getName();
        Long creatorId = userService.findEntityByEmail(userEmail).getId();
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(request, creatorId));
    }

    // PUT /api/products/{id}
    // Actualiza un producto existente.
    // Solo el creador del producto o un administrador pueden actualizar.
    @PutMapping("/{id}")
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        return productService.updateProduct(id, request);
    }

    // DELETE /api/products/{id}
    // Elimina un producto completamente.
    // Solo el creador del producto o un administrador pueden eliminar.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
