package com.cinema.film.controller;

import com.cinema.film.model.Seance;
import com.cinema.film.repository.SeanceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/seances")
public class SeanceController {

    private final SeanceRepository seanceRepository;

    // CONSTRUCTEUR AVEC INJECTION
    public SeanceController(SeanceRepository seanceRepository) {
        this.seanceRepository = seanceRepository;
    }

    // 1. GET toutes les séances
    @GetMapping
    public List<Seance> getAllSeances() {
        return seanceRepository.findAll();
    }

    // 2. GET séance par ID (POUR FEIGN CLIENT)
    @GetMapping("/{id}")
    public ResponseEntity<Seance> getSeanceById(@PathVariable Long id) {
        Optional<Seance> seance = seanceRepository.findById(id);
        return seance.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. GET séances par film ID
    @GetMapping("/film/{filmId}")
    public List<Seance> getSeancesByFilm(@PathVariable Long filmId) {
        return seanceRepository.findByFilmId(filmId);
    }

    // 4. PUT pour mettre à jour les places (POUR FEIGN CLIENT)
    @PutMapping("/{id}/places")
    public ResponseEntity<Seance> updatePlaces(@PathVariable Long id,
                                               @RequestBody Map<String, Integer> updates) {
        Optional<Seance> optionalSeance = seanceRepository.findById(id);

        if (optionalSeance.isPresent()) {
            Seance seance = optionalSeance.get();

            // Met à jour uniquement les places disponibles
            if (updates.containsKey("placesDisponibles")) {
                Integer nouvellesPlaces = updates.get("placesDisponibles");
                seance.setPlacesDisponibles(nouvellesPlaces);
            }

            Seance updatedSeance = seanceRepository.save(seance);
            return ResponseEntity.ok(updatedSeance);
        }

        return ResponseEntity.notFound().build();
    }

    // 5. POST créer une nouvelle séance
    @PostMapping
    public Seance createSeance(@RequestBody Seance seance) {
        // Initialise les places disponibles avec la capacité de la salle si non fourni
        if (seance.getPlacesDisponibles() == null) {
            seance.setPlacesDisponibles(seance.getSalle().getCapacite());
        }
        return seanceRepository.save(seance);
    }

    // BONUS: Endpoint simple pour tester
    @GetMapping("/test")
    public String test() {
        return "Seance controller is working!";
    }
}