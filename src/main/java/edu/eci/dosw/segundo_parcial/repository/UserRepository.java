package edu.eci.dosw.segundo_parcial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.eci.dosw.segundo_parcial.entity.UserEntity;

/**
 * Repositorio con las consultas basicas de usuarios.
 *
 * Si necesitas buscar por otro campo, agrega un metodo aqui siguiendo la convencion de Spring Data.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
