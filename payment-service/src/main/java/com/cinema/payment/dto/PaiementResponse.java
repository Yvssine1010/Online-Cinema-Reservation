// src/main/java/com/cinema/payment/dto/PaiementResponse.java  
package com.cinema.payment.dto;

import lombok.Data;
import com.cinema.payment.model.Paiement.StatutPaiement;

@Data
public class PaiementResponse {
    private boolean success;
    private String message;
    private String transactionId;
    private StatutPaiement statut;

    // Getters/Setters manuels si Lombok ne marche pas :
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public StatutPaiement getStatut() { return statut; }
    public void setStatut(StatutPaiement statut) { this.statut = statut; }
}