// ReponseReservation.java - VERSION CORRIGÉE
package com.cinema.reservation.dto;

import lombok.Data;
import com.cinema.reservation.model.Reservation.StatutReservation;  // 👈 Ça devrait marcher maintenant

@Data
public class ReponseReservation {
    private Long reservationId;
    private StatutReservation statut;
    private String message;
    private String referencePaiement;

    // Getters/Setters si Lombok ne marche pas
    public Long getReservationId() { return reservationId; }
    public void setReservationId(Long reservationId) { this.reservationId = reservationId; }

    public StatutReservation getStatut() { return statut; }
    public void setStatut(StatutReservation statut) { this.statut = statut; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getReferencePaiement() { return referencePaiement; }
    public void setReferencePaiement(String referencePaiement) { this.referencePaiement = referencePaiement; }
}