package edu.eci.dosw.segundo_parcial.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.eci.dosw.segundo_parcial.entity.PaymentEntity;

/**
 * Repositorio para pagos de usuarios.
 */
@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    List<PaymentEntity> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<PaymentEntity> findByIdAndUserId(Long paymentId, Long userId);
}
