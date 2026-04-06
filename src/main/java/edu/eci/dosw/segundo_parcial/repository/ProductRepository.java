package edu.eci.dosw.segundo_parcial.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.eci.dosw.segundo_parcial.entity.ProductEntity;

/**
 * Repositorio para acceder a datos de productos en la BD.
 *
 * Extiende BaseRepository para heredar métodos CRUD básicos.
 * ¿Qué cambiar?:
 * - Si necesitas búsquedas específicas (ej: findByPriceRange, findByNameLike), agrega métodos aquí
 * - Las búsquedas complejas pueden usar @Query para escribir JPQL o SQL nativo
 */
@Repository
public interface ProductRepository extends BaseRepository<ProductEntity, Long> {

    // Busca todos los productos que pertenecen a una categoría específica.
    // Útil para listar productos filtrados por categoría.
    List<ProductEntity> findByCategory_Id(Long categoryId);

    // Busca todos los productos creados por un usuario específico (vendedor).
    // Permite que cada vendedor vea sus propios productos.
    List<ProductEntity> findByCreator_Id(Long creatorId);

    // Busca productos por disponibilidad.
    // Permite mostrar solo productos disponibles o listar productos descontinuados.
    List<ProductEntity> findByAvailable(boolean available);

    // Busca productos que coincidan parcialmente con un nombre (case-insensitive).
    // Útil para implementar búsqueda por nombre en la API.
    List<ProductEntity> findByNameContainingIgnoreCase(String name);

    // Busca productos por categoría y disponibilidad combinadas.
    // Useful para filtros avanzados.
    List<ProductEntity> findByCategoryIdAndAvailable(Long categoryId, boolean available);
}
