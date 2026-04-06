package edu.eci.dosw.segundo_parcial.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.eci.dosw.segundo_parcial.dto.CategoryRequest;
import edu.eci.dosw.segundo_parcial.dto.CategoryResponse;
import edu.eci.dosw.segundo_parcial.entity.CategoryEntity;
import edu.eci.dosw.segundo_parcial.exception.ResourceNotFoundException;
import edu.eci.dosw.segundo_parcial.repository.CategoryRepository;

/**
 * Servicio para operaciones CRUD de categorías.
 *
 * Centraliza la lógica de negocio para categorías.
 * ¿Qué cambiar?:
 * - Si necesitas validaciones especiales para categorías, agrega métodos privados aquí
 * - Si agregas relaciones (ej: subcategorías), aquí es donde manejas la lógica
 * - Los métodos en @Transactional(readOnly=true) no pueden modificar datos
 */
@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Obtiene todas las categorías activas.
    // readOnly=true porque es una lectura sin cambios.
    public List<CategoryResponse> findAllActiveCategories() {
        return categoryRepository.findByActive(true)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Obtiene todas las categorías (incluyendo inactivas).
    // Útil para administradores que quieren ver todo.
    public List<CategoryResponse> findAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Obtiene una categoría específica por ID.
    // Lanza ResourceNotFoundException si no existe.
    public CategoryResponse findCategoryById(Long id) {
        return toResponse(findEntityById(id));
    }

    // Crea una nueva categoría.
    // Valida que el nombre sea único.
    // @Transactional permite modificar datos en la BD.
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        // Validar que el nombre no exista ya.
        if (categoryRepository.existsByName(request.getName().trim())) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre");
        }

        CategoryEntity category = new CategoryEntity();
        category.setName(request.getName().trim());
        category.setDescription(request.getDescription());
        category.setActive(true);

        return toResponse(categoryRepository.save(category));
    }

    // Actualiza una categoría existente.
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        CategoryEntity category = findEntityById(id);

        // Validar que si cambias el nombre, no exista otro con ese nombre.
        if (!category.getName().equals(request.getName()) && categoryRepository.existsByName(request.getName().trim())) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre");
        }

        category.setName(request.getName().trim());
        category.setDescription(request.getDescription());

        return toResponse(categoryRepository.save(category));
    }

    // Desactiva una categoría (soft delete).
    // Mejor que eliminar porque no loses datos de productos que la referencian.
    @Transactional
    public CategoryResponse deactivateCategory(Long id) {
        CategoryEntity category = findEntityById(id);
        category.setActive(false);
        return toResponse(categoryRepository.save(category));
    }

    // Elimina completamente una categoría.
    // Cuidado: solo si no hay productos que la usen.
    @Transactional
    public void deleteCategory(Long id) {
        CategoryEntity category = findEntityById(id);
        categoryRepository.delete(category);
    }

    // Obtiene la entidad para uso interno (no se expone en respuestas).
    private CategoryEntity findEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
    }

    // Convierte una entidad a su DTO de respuesta.
    private CategoryResponse toResponse(CategoryEntity category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.isActive(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
