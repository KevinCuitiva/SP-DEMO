package edu.eci.dosw.segundo_parcial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Interfaz base para todos los repositorios.
 *
 * Define métodos CRUD comunes que pueden ser reutilizados.
 * ¿Qué cambiar?: Si necesitas métodos de búsqueda específicos para una entidad,
 * extiende este repositorio base y agrega métodos específicos (ej: findByStatus(), findByCreatedAfter()).
 *
 * @param <T> Tipo de entidad
 * @param <ID> Tipo del ID (generalmente Long)
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
    // Hereda automáticamente: save, findById, findAll, update, delete, etc.
    // Todos los métodos básicos de Spring Data JPA están disponibles.
}
