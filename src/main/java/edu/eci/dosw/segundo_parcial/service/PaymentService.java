package edu.eci.dosw.segundo_parcial.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.eci.dosw.segundo_parcial.dto.PaymentRequest;
import edu.eci.dosw.segundo_parcial.dto.PaymentResponse;
import edu.eci.dosw.segundo_parcial.entity.PaymentEntity;
import edu.eci.dosw.segundo_parcial.entity.UserEntity;
import edu.eci.dosw.segundo_parcial.exception.ResourceNotFoundException;
import edu.eci.dosw.segundo_parcial.repository.PaymentRepository;
import edu.eci.dosw.segundo_parcial.repository.UserRepository;

/**
 * Logica de pagos asociados a usuarios.
 */
@Service
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public PaymentService(PaymentRepository paymentRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PaymentResponse createPayment(Long userId, PaymentRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        PaymentEntity payment = new PaymentEntity();
        payment.setUser(user);
        payment.setAmount(request.getAmount());
        payment.setConcept(request.getConcept().trim());

        return toResponse(paymentRepository.save(payment));
    }

    public List<PaymentResponse> findPaymentsByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        return paymentRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    public PaymentResponse findPaymentByUserAndId(Long userId, Long paymentId) {
        PaymentEntity payment = paymentRepository.findByIdAndUserId(paymentId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));
        return toResponse(payment);
    }

    private PaymentResponse toResponse(PaymentEntity payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getUser().getId(),
                payment.getAmount(),
                payment.getConcept(),
                payment.getCreatedAt());
    }
}
