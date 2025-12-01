// FilmServiceClient.java
package com.cinema.reservation.client;

import com.cinema.reservation.dto.Seance;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "film-service", url = "http://localhost:8081")
public interface FilmServiceClient {

    // Récupérer une séance par ID
    @GetMapping("/api/seances/{id}")
    Seance getSeanceById(@PathVariable Long id);

    // Mettre à jour les places disponibles
    @PatchMapping("/api/seances/{id}")
    void updatePlacesDisponibles(@PathVariable Long id, @RequestBody Map<String, Integer> updates);
}