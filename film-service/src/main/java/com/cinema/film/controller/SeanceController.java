package com.cinema.film.controller;

import com.cinema.film.model.Seance;
import com.cinema.film.repository.SeanceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seances")
public class SeanceController {

    private final SeanceRepository seanceRepository;

    public SeanceController(SeanceRepository seanceRepository) {
        this.seanceRepository = seanceRepository;
    }

    @GetMapping
    public List<Seance> getAllSeances() {
        return seanceRepository.findAll();
    }

    @GetMapping("/film/{filmId}")
    public List<Seance> getSeancesByFilm(@PathVariable Long filmId) {
        return seanceRepository.findByFilmId(filmId);
    }

    @PostMapping
    public Seance createSeance(@RequestBody Seance seance) {
        // Initialiser les places disponibles avec la capacité de la salle
        if (seance.getPlacesDisponibles() == null) {
            seance.setPlacesDisponibles(seance.getSalle().getCapacite());
        }
        return seanceRepository.save(seance);
    }
}