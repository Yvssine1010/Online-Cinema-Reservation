// ReservationService.java
package com.cinema.reservation.service;

import com.cinema.reservation.client.FilmServiceClient;
import com.cinema.reservation.dto.Seance;
import com.cinema.reservation.model.Reservation;
import com.cinema.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final FilmServiceClient filmServiceClient;

    public ReservationService(ReservationRepository reservationRepository,
                              FilmServiceClient filmServiceClient) {
        this.reservationRepository = reservationRepository;
        this.filmServiceClient = filmServiceClient;
    }

    public Reservation creerReservation(Long seanceId, Long utilisateurId, Integer nombrePlaces) {
        // 1. Vérifier la disponibilité via FeignClient
        Seance seance = filmServiceClient.getSeanceById(seanceId);

        if (seance == null) {
            throw new RuntimeException("Séance non trouvée");
        }

        if (seance.getPlacesDisponibles() < nombrePlaces) {
            throw new RuntimeException("Places insuffisantes");
        }

        // 2. Créer la réservation
        Reservation reservation = new Reservation();
        reservation.setSeanceId(seanceId);
        reservation.setUtilisateurId(utilisateurId);
        reservation.setNombrePlaces(nombrePlaces);
        reservation.setStatut(Reservation.StatutReservation.EN_ATTENTE);

        Reservation savedReservation = reservationRepository.save(reservation);

        // 3. Mettre à jour les places disponibles
        Map<String, Integer> updates = new HashMap<>();
        updates.put("placesDisponibles", seance.getPlacesDisponibles() - nombrePlaces);
        filmServiceClient.updatePlacesDisponibles(seanceId, updates);

        return savedReservation;
    }
}