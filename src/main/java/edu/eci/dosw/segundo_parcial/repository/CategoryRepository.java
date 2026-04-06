package edu.eci.dosw.segundo_parcial.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import edu.eci.dosw.segundo_parcial.entity.CategoryEntity;

/**
 * Repositorio para acceder a datos de categorías en la BD.
 *
 * Extiende BaseRepository para heredar métodos CRUD básicos (save, findById, findAll, delete).
 * ¿Qué cambiar?:
 * - Si necesitas búsquedas específicas (ej: findByNameContaining, findByActive), agrega métodos aquí
 * - Spring Data genera automáticamente las queries SQL basadas en el nombre del método
 */
@Repository
public interface CategoryRepository extends BaseRepository<CategoryEntity, Long> {

    // Busca una categoría por nombre exacto.
    // Retorna Optional para manejar casos donde la categoría no existe.
    Optional<CategoryEntity> findByName(String name);

    // Busca todas las categorías activas.
    // Útil para listar solo categorías disponibles en la tienda.
    List<CategoryEntity> findByActive(boolean active);

    // Verifica si ya existe una categoría con ese nombre.
    // Se usa en validaciones antes de crear una nueva categoría.
    boolean existsByName(String name);
}
