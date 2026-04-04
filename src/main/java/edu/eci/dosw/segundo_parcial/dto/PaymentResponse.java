package edu.eci.dosw.segundo_parcial.dto;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Salida publica de pagos.
 */
public class PaymentResponse {

    private Long id;
    private Long userId;
    private BigDecimal amount;
    private String concept;
    private Instant createdAt;

    public PaymentResponse() {
    }

    public PaymentResponse(Long id, Long userId, BigDecimal amount, String concept, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.concept = concept;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
