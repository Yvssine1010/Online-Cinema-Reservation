// Reservation.java - VERSION COMPLÈTE
package com.cinema.reservation.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long seanceId;
    private Long utilisateurId;
    private Integer nombrePlaces;

    @Enumerated(EnumType.STRING)  // 👈 IMPORTANT
    private StatutReservation statut = StatutReservation.EN_ATTENTE;

    private LocalDateTime dateCreation;
    private String referencePaiement;

    // 👇 AJOUTE CET ENUM DANS LA CLASSE
    public enum StatutReservation {
        EN_ATTENTE, CONFIRMEE, ANNULEE, EXPIREE
    }

    // Constructeur pour initialiser la date
    public Reservation() {
        this.dateCreation = LocalDateTime.now();
    }
}