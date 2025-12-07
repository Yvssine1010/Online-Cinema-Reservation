package com.cinema.reservation.dto;

public class PaiementResponse {
    private boolean success;
    private String message;
    private String transactionId;
    private String statut;

    // GETTERS - TRÈS IMPORTANT
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getTransactionId() { return transactionId; }
    public String getStatut() { return statut; }

    // SETTERS
    public void setSuccess(boolean success) { this.success = success; }
    public void setMessage(String message) { this.message = message; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public void setStatut(String statut) { this.statut = statut; }
}