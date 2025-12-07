package com.cinema.reservation.service;

import com.cinema.reservation.client.FilmServiceClient;
import com.cinema.reservation.client.PaymentServiceClient;
import com.cinema.reservation.dto.PaiementRequest;
import com.cinema.reservation.dto.PaiementResponse;
import com.cinema.reservation.dto.Seance;
import com.cinema.reservation.model.Reservation;
import com.cinema.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final FilmServiceClient filmServiceClient;
    private final PaymentServiceClient paymentServiceClient;  // NOUVEAU

    public ReservationService(ReservationRepository reservationRepository,
                              FilmServiceClient filmServiceClient,
                              PaymentServiceClient paymentServiceClient) {  // MODIFIÉ
        this.reservationRepository = reservationRepository;
        this.filmServiceClient = filmServiceClient;
        this.paymentServiceClient = paymentServiceClient;  // NOUVEAU
    }

    public Reservation creerReservation(Long seanceId, Long utilisateurId, Integer nombrePlaces) {
        // 1. Vérifier la disponibilité via FeignClient
        Seance seance = filmServiceClient.getSeanceById(seanceId);

        if (seance == null) {
            throw new RuntimeException("Séance non trouvée");
        }

        if (seance.getPlacesDisponibles() < nombrePlaces) {
            throw new RuntimeException("Places insuffisantes. Disponibles: " + seance.getPlacesDisponibles());
        }

        // 2. Créer la réservation (statut EN_ATTENTE)
        Reservation reservation = new Reservation();
        reservation.setSeanceId(seanceId);
        reservation.setUtilisateurId(utilisateurId);
        reservation.setNombrePlaces(nombrePlaces);
        reservation.setStatut(Reservation.StatutReservation.EN_ATTENTE);

        Reservation savedReservation = reservationRepository.save(reservation);

        try {
            // 3. Traiter le paiement via payment-service
            PaiementRequest paiementRequest = new PaiementRequest();
            paiementRequest.setReservationId(savedReservation.getId());
            paiementRequest.setMontant(calculerMontant(seance, nombrePlaces));
            paiementRequest.setEmailClient("client@cinema.com");  // À remplacer par vrai email

            PaiementResponse paiementResponse = paymentServiceClient.processPayment(paiementRequest);

            // 4. Mettre à jour le statut selon le résultat du paiement
            if (paiementResponse.isSuccess()) {
                savedReservation.setStatut(Reservation.StatutReservation.CONFIRMEE);
                savedReservation.setReferencePaiement(paiementResponse.getTransactionId());

                // 5. Mettre à jour les places disponibles (seulement si paiement réussi)
                Map<String, Integer> updates = new HashMap<>();
                updates.put("placesDisponibles", seance.getPlacesDisponibles() - nombrePlaces);
                filmServiceClient.updatePlacesDisponibles(seanceId, updates);

            } else {
                savedReservation.setStatut(Reservation.StatutReservation.ANNULEE);
                // Ne pas mettre à jour les places si paiement échoue
            }

        } catch (Exception e) {
            // En cas d'erreur de paiement, annuler la réservation
            savedReservation.setStatut(Reservation.StatutReservation.ANNULEE);
            throw new RuntimeException("Erreur lors du paiement: " + e.getMessage());
        }

        // 6. Sauvegarder les modifications
        return reservationRepository.save(savedReservation);
    }

    // Méthode pour annuler une réservation
    public Reservation annulerReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        // Vérifier si on peut annuler (seulement si EN_ATTENTE ou CONFIRMEE)
        if (reservation.getStatut() == Reservation.StatutReservation.EN_ATTENTE ||
                reservation.getStatut() == Reservation.StatutReservation.CONFIRMEE) {

            reservation.setStatut(Reservation.StatutReservation.ANNULEE);

            // Si réservation confirmée, libérer les places
            if (reservation.getStatut() == Reservation.StatutReservation.CONFIRMEE) {
                libererPlaces(reservation.getSeanceId(), reservation.getNombrePlaces());
            }

            return reservationRepository.save(reservation);
        }

        throw new RuntimeException("Impossible d'annuler cette réservation");
    }

    // Méthode privée pour libérer les places
    private void libererPlaces(Long seanceId, Integer nombrePlaces) {
        try {
            Seance seance = filmServiceClient.getSeanceById(seanceId);
            if (seance != null) {
                Map<String, Integer> updates = new HashMap<>();
                updates.put("placesDisponibles", seance.getPlacesDisponibles() + nombrePlaces);
                filmServiceClient.updatePlacesDisponibles(seanceId, updates);
            }
        } catch (Exception e) {
            // Log l'erreur mais ne pas bloquer l'annulation
            System.err.println("Erreur lors de la libération des places: " + e.getMessage());
        }
    }

    // Méthode privée pour calculer le montant
    private BigDecimal calculerMontant(Seance seance, Integer nombrePlaces) {
        if (seance.getPrix() == null) {
            throw new RuntimeException("Prix non défini pour cette séance");
        }
        return seance.getPrix().multiply(BigDecimal.valueOf(nombrePlaces));
    }

    // Méthode pour vérifier le statut d'une réservation
    public String getStatutReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        return reservation.getStatut().toString();
    }
}