package com.cinema.reservation.controller;

import com.cinema.reservation.client.FilmServiceClient;
import com.cinema.reservation.model.Reservation;
import com.cinema.reservation.repository.ReservationRepository;
import com.cinema.reservation.service.ReservationService;  // 👈 IMPORT AJOUTÉ
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final FilmServiceClient filmServiceClient;

    // 👇 CONSTRUCTEUR MIS À JOUR
    public ReservationController(ReservationRepository reservationRepository,
                                 ReservationService reservationService,
                                 FilmServiceClient filmServiceClient) {
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
        this.filmServiceClient = filmServiceClient;
    }


    @GetMapping("/test")
    public String test() {
        return "Reservation service is working!";
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
    @GetMapping("/test-feign/{seanceId}")
    public String testFeign(@PathVariable Long seanceId) {
        try {
            // Test simple pour vérifier que FeignClient fonctionne
            Object seance = filmServiceClient.getSeanceById(seanceId);
            return "FeignClient fonctionne ! Séance: " + seance;
        } catch (Exception e) {
            return "Erreur FeignClient: " + e.getMessage();
        }
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @PostMapping("/creer")
    public Reservation creerReservation(@RequestBody Map<String, Object> request) {
        Long seanceId = Long.valueOf(request.get("seanceId").toString());
        Long utilisateurId = Long.valueOf(request.get("utilisateurId").toString());
        Integer nombrePlaces = Integer.valueOf(request.get("nombrePlaces").toString());

        return reservationService.creerReservation(seanceId, utilisateurId, nombrePlaces);
    }
}