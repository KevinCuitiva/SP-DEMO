package edu.eci.dosw.segundo_parcial.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.dosw.segundo_parcial.dto.PaymentRequest;
import edu.eci.dosw.segundo_parcial.dto.PaymentResponse;
import edu.eci.dosw.segundo_parcial.service.PaymentService;
import jakarta.validation.Valid;

/**
 * Endpoints para pagos asociados a un usuario.
 */
@RestController
@RequestMapping("/api/users/{userId}/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<PaymentResponse> getPaymentsByUser(@PathVariable Long userId) {
        return paymentService.findPaymentsByUser(userId);
    }

    @GetMapping("/{paymentId}")
    public PaymentResponse getPaymentById(@PathVariable Long userId, @PathVariable Long paymentId) {
        return paymentService.findPaymentByUserAndId(userId, paymentId);
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@PathVariable Long userId,
            @Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPayment(userId, request));
    }
}
