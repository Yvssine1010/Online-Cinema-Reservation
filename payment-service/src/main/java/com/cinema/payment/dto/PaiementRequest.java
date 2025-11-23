package com.cinema.payment.dto;

import lombok.Data;
import java.math.BigDecimal;
/*Il sert à transporter les données depuis le client (par exemple une requête HTTP) vers le backend sans exposer directement l’entité Paiement.

Il permet de séparer la couche présentation de la couche persistence, en ne contenant que les champs nécessaires pour la requête.

Ici, il capture les informations d’un paiement que l’utilisateur envoie : reservationId, montant, et les infos de carte bancaire.*/
@Data
public class PaiementRequest {
    private Long reservationId;
    private BigDecimal montant;
    private String numCarte;
    private String dateExpiration;
    private String cryptogramme;
    private String emailClient;

    public PaiementRequest() {
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public String getNumCarte() {
        return numCarte;
    }

    public void setNumCarte(String numCarte) {
        this.numCarte = numCarte;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public String getCryptogramme() {
        return cryptogramme;
    }

    public void setCryptogramme(String cryptogramme) {
        this.cryptogramme = cryptogramme;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }
}