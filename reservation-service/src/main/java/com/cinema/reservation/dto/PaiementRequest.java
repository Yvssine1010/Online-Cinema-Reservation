// src/main/java/com/cinema/reservation/dto/PaiementRequest.java
package com.cinema.reservation.dto;

import java.math.BigDecimal;

public class PaiementRequest {
    private Long reservationId;
    private BigDecimal montant;
    private String emailClient;

    // Constructeur par défaut
    public PaiementRequest() {}

    // Constructeur avec paramètres
    public PaiementRequest(Long reservationId, BigDecimal montant, String emailClient) {
        this.reservationId = reservationId;
        this.montant = montant;
        this.emailClient = emailClient;
    }

    // GETTERS
    public Long getReservationId() {
        return reservationId;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public String getEmailClient() {
        return emailClient;
    }

    // SETTERS
    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }
}