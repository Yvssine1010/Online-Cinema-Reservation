// src/main/java/com/cinema/payment/service/PaymentService.java
package com.cinema.payment.service;

import com.cinema.payment.dto.PaiementRequest;
import com.cinema.payment.dto.PaiementResponse;
import com.cinema.payment.model.Paiement;
import com.cinema.payment.repository.PaiementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaiementRepository paiementRepository;

    public PaymentService(PaiementRepository paiementRepository) {
        this.paiementRepository = paiementRepository;
    }

    public PaiementResponse processPayment(PaiementRequest request) {
        // Simuler un paiement (toujours réussi pour le moment)
        Paiement paiement = new Paiement();
        paiement.setReservationId(request.getReservationId());
        paiement.setMontant(request.getMontant());
        paiement.setEmailClient(request.getEmailClient());
        paiement.setStatut(Paiement.StatutPaiement.SUCCES);
        paiement.setReferenceTransaction(UUID.randomUUID().toString());
        paiement.setDatePaiement(LocalDateTime.now());

        Paiement savedPaiement = paiementRepository.save(paiement);

        PaiementResponse response = new PaiementResponse();
        response.setSuccess(true);
        response.setMessage("Paiement traité avec succès");
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
}