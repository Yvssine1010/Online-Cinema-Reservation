package com.cinema.reservation.client;

import com.cinema.reservation.dto.Seance;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "film-service")
public interface FilmServiceClient {

    // Récupérer une séance par ID
    @GetMapping("/api/seances/{id}")
    Seance getSeanceById(@PathVariable Long id);

    // Mettre à jour les places disponibles
    @PutMapping("/api/seances/{id}/places")
    void updatePlacesDisponibles(@PathVariable Long id, @RequestBody Map<String, Integer> updates);
}