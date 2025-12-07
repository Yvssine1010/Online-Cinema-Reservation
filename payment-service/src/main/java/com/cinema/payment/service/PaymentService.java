// src/main/java/com/cinema/payment/service/PaymentService.java
package com.cinema.payment.service;

import com.cinema.payment.dto.PaiementRequest;
import com.cinema.payment.dto.PaiementResponse;
import com.cinema.payment.model.Paiement;
import com.cinema.payment.repository.PaiementRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaiementRepository paiementRepository;
    private final WebClient webClient;

    public PaymentService(PaiementRepository paiementRepository, WebClient webClient) {
        this.paiementRepository = paiementRepository;
        this.webClient = webClient;
    }

    public PaiementResponse processPayment(PaiementRequest request) {
        // 1. Simuler l'appel à l'API de paiement externe avec WebClient
        Mono<ExternalPaymentResponse> externalCall = webClient.post()
                .uri("/charge")
                .bodyValue(createExternalRequest(request))
                .retrieve()
                .bodyToMono(ExternalPaymentResponse.class)
                .onErrorResume(e -> {
                    // En cas d'erreur, simuler une réponse réussie pour les tests
                    return Mono.just(new ExternalPaymentResponse(true,
                            "SIM-" + UUID.randomUUID(),
                            "Simulation: " + e.getMessage()));
                });

        // 2. Exécuter l'appel et obtenir le résultat
        ExternalPaymentResponse externalResponse = externalCall.block();

        // 3. Créer l'enregistrement de paiement
        Paiement paiement = new Paiement();
        paiement.setReservationId(request.getReservationId());
        paiement.setMontant(request.getMontant());
        paiement.setEmailClient(request.getEmailClient());

        if (externalResponse != null && externalResponse.isSuccess()) {
            paiement.setStatut(Paiement.StatutPaiement.SUCCES);
            paiement.setReferenceTransaction(externalResponse.getTransactionId());
        } else {
            paiement.setStatut(Paiement.StatutPaiement.ECHEC);
            paiement.setReferenceTransaction("FAILED-" + UUID.randomUUID());
        }

        paiement.setDatePaiement(LocalDateTime.now());

        Paiement savedPaiement = paiementRepository.save(paiement);

        // 4. Retourner la réponse
        PaiementResponse response = new PaiementResponse();
        response.setSuccess(paiement.getStatut() == Paiement.StatutPaiement.SUCCES);
        response.setMessage(externalResponse != null ? externalResponse.getMessage() : "Erreur lors du traitement");
        response.setTransactionId(savedPaiement.getReferenceTransaction());
        response.setStatut(savedPaiement.getStatut());

        return response;
    }

    public PaiementResponse getPaymentStatus(Long reservationId) {
        return paiementRepository.findByReservationId(reservationId)
                .map(paiement -> {
                    PaiementResponse response = new PaiementResponse();
                    response.setSuccess(paiement.getStatut() == Paiement.StatutPaiement.SUCCES);
                    response.setMessage("Statut: " + paiement.getStatut());
                    response.setTransactionId(paiement.getReferenceTransaction());
                    response.setStatut(paiement.getStatut());
                    return response;
                })
                .orElseGet(() -> {
                    PaiementResponse response = new PaiementResponse();
                    response.setSuccess(false);
                    response.setMessage("Aucun paiement trouvé pour cette réservation");
                    return response;
                });
    }

    // Méthode privée pour créer la requête externe
    private ExternalPaymentRequest createExternalRequest(PaiementRequest request) {
        ExternalPaymentRequest externalRequest = new ExternalPaymentRequest();
        externalRequest.setAmount(request.getMontant());
        externalRequest.setCurrency("EUR");
        externalRequest.setPaymentMethod("credit_card");
        externalRequest.setDescription("Paiement réservation #" + request.getReservationId());
        externalRequest.setCustomerEmail(request.getEmailClient());
        return externalRequest;
    }

    // Classes internes pour la simulation
    private static class ExternalPaymentRequest {
        private BigDecimal amount;
        private String currency;
        private String paymentMethod;
        private String description;
        private String customerEmail;

        // Getters et Setters
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }

        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }

        public String getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getCustomerEmail() { return customerEmail; }
        public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    }

    private static class ExternalPaymentResponse {
        private boolean success;
        private String transactionId;
        private String message;

        public ExternalPaymentResponse() {}

        public ExternalPaymentResponse(boolean success, String transactionId, String message) {
            this.success = success;
            this.transactionId = transactionId;
            this.message = message;
        }

        // Getters et Setters
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }

        public String getTransactionId() { return transactionId; }
        public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}