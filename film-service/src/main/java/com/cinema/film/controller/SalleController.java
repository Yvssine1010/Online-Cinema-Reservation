package com.cinema.film.controller;

import com.cinema.film.model.Salle;
import com.cinema.film.repository.SalleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salles")
public class SalleController {

    private final SalleRepository salleRepository;

    public SalleController(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
    }

    @GetMapping
    public List<Salle> getAllSalles() {
        return salleRepository.findAll();
    }

    @PostMapping
    public Salle createSalle(@RequestBody Salle salle) {
        return salleRepository.save(salle);
    }
    @GetMapping("/{id}")
    public Salle getSalleById(@PathVariable Long id) {
        return salleRepository.findById(id)
                .orElse(null); // ou tu peux renvoyer une exception personnalisée
    }

}