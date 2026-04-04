package edu.eci.dosw.segundo_parcial.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.eci.dosw.segundo_parcial.dto.PaymentRequest;
import edu.eci.dosw.segundo_parcial.dto.PaymentResponse;
import edu.eci.dosw.segundo_parcial.entity.PaymentEntity;
import edu.eci.dosw.segundo_parcial.entity.Role;
import edu.eci.dosw.segundo_parcial.entity.UserEntity;
import edu.eci.dosw.segundo_parcial.exception.ResourceNotFoundException;
import edu.eci.dosw.segundo_parcial.repository.PaymentRepository;
import edu.eci.dosw.segundo_parcial.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void createPaymentSavesPaymentForExistingUser() {
        UserEntity user = new UserEntity("User", "user@example.com", "encoded", Role.USER);
        user.setId(1L);

        PaymentRequest request = new PaymentRequest();
        request.setAmount(new BigDecimal("125000.00"));
        request.setConcept("Matricula");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(paymentRepository.save(any(PaymentEntity.class))).thenAnswer(invocation -> {
            PaymentEntity payment = invocation.getArgument(0);
            payment.setId(10L);
            payment.setCreatedAt(Instant.parse("2026-04-04T20:30:00Z"));
            return payment;
        });

        PaymentResponse response = paymentService.createPayment(1L, request);

        assertEquals(10L, response.getId());
        assertEquals(1L, response.getUserId());
        assertEquals(new BigDecimal("125000.00"), response.getAmount());
    }

    @Test
    void listPaymentsByUserThrowsWhenUserDoesNotExist() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> paymentService.findPaymentsByUser(99L));
    }

    @Test
    void findPaymentByUserAndIdReturnsPayment() {
        UserEntity user = new UserEntity("User", "user@example.com", "encoded", Role.USER);
        user.setId(2L);

        PaymentEntity payment = new PaymentEntity();
        payment.setId(8L);
        payment.setUser(user);
        payment.setAmount(new BigDecimal("80000.00"));
        payment.setConcept("Laboratorio");
        payment.setCreatedAt(Instant.parse("2026-04-04T20:35:00Z"));

        when(paymentRepository.findByIdAndUserId(8L, 2L)).thenReturn(Optional.of(payment));

        PaymentResponse response = paymentService.findPaymentByUserAndId(2L, 8L);

        assertEquals(8L, response.getId());
        assertEquals("Laboratorio", response.getConcept());
    }

    @Test
    void findPaymentsByUserReturnsOrderedList() {
        UserEntity user = new UserEntity("User", "user@example.com", "encoded", Role.USER);
        user.setId(3L);

        PaymentEntity first = new PaymentEntity();
        first.setId(5L);
        first.setUser(user);
        first.setAmount(new BigDecimal("50000.00"));
        first.setConcept("Seguro");
        first.setCreatedAt(Instant.parse("2026-04-04T21:00:00Z"));

        when(userRepository.existsById(3L)).thenReturn(true);
        when(paymentRepository.findByUserIdOrderByCreatedAtDesc(3L)).thenReturn(List.of(first));

        List<PaymentResponse> responses = paymentService.findPaymentsByUser(3L);

        assertEquals(1, responses.size());
        assertEquals("Seguro", responses.get(0).getConcept());
    }
}
