package edu.eci.dosw.segundo_parcial.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.dosw.segundo_parcial.dto.CategoryRequest;
import edu.eci.dosw.segundo_parcial.dto.CategoryResponse;
import edu.eci.dosw.segundo_parcial.service.CategoryService;
import jakarta.validation.Valid;

/**
 * Endpoints para gestionar categorías de productos.
 *
 * ¿Qué cambiar?:
 * - Si agregas más campos a CategoryEntity, los endpoints automáticamente los soportarán
 * - Las restricciones de seguridad están en SecurityConfig (quién puede crear/actualizar/eliminar)
 * - Si necesitas más endpoints (ej: para reordenar, búsqueda avanzada), agrega aquí
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // GET /api/categories
    // Obtiene todas las categorías activas disponibles para los clientes.
    @GetMapping
    public List<CategoryResponse> getAllActiveCategories() {
        return categoryService.findAllActiveCategories();
    }

    // GET /api/categories/{id}
    // Obtiene una categoría específica por su ID.
    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return categoryService.findCategoryById(id);
    }

    // POST /api/categories
    // Crea una nueva categoría.
    // Solo administradores pueden crear categorías (validado en SecurityConfig).
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(request));
    }

    // PUT /api/categories/{id}
    // Actualiza una categoría existente.
    // Solo administradores pueden actualizar (validado en SecurityConfig).
    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        return categoryService.updateCategory(id, request);
    }

    // DELETE /api/categories/{id}
    // Elimina una categoría completamente.
    // Cuidado: asegúrate de no tener productos que la usen.
    // Solo administradores pueden eliminar (validado en SecurityConfig).
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
