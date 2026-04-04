package edu.eci.dosw.segundo_parcial.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.eci.dosw.segundo_parcial.dto.PaymentRequest;
import edu.eci.dosw.segundo_parcial.dto.PaymentResponse;
import edu.eci.dosw.segundo_parcial.service.PaymentService;

class PaymentControllerTest {

    @Test
    void getPaymentsByUserDelegatesToService() {
        PaymentService paymentService = Mockito.mock(PaymentService.class);
        PaymentController controller = new PaymentController(paymentService);

        when(paymentService.findPaymentsByUser(1L)).thenReturn(List.of(
                new PaymentResponse(1L, 1L, new BigDecimal("50000.00"), "Seguro", Instant.parse("2026-04-04T21:00:00Z"))));

        List<PaymentResponse> responses = controller.getPaymentsByUser(1L);

        assertEquals(1, responses.size());
        verify(paymentService).findPaymentsByUser(1L);
    }

    @Test
    void createPaymentReturnsCreatedResponse() {
        PaymentService paymentService = Mockito.mock(PaymentService.class);
        PaymentController controller = new PaymentController(paymentService);

        PaymentRequest request = new PaymentRequest();
        request.setAmount(new BigDecimal("120000.00"));
        request.setConcept("Matricula");

        when(paymentService.createPayment(2L, request)).thenReturn(
                new PaymentResponse(9L, 2L, new BigDecimal("120000.00"), "Matricula", Instant.parse("2026-04-04T21:10:00Z")));

        PaymentResponse response = controller.createPayment(2L, request).getBody();

        assertEquals(9L, response.getId());
        verify(paymentService).createPayment(2L, request);
    }
}
