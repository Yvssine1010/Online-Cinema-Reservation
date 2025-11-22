package com.cinema.film.controller;

import com.cinema.film.model.Film;
import com.cinema.film.repository.FilmRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    private final FilmRepository filmRepository;

    public FilmController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    // GET all films
    @GetMapping
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    // GET film by ID
    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable Long id) {
        Optional<Film> film = filmRepository.findById(id);
        return film.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE film
    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        return filmRepository.save(film);
    }

    // UPDATE film
    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable Long id, @RequestBody Film filmDetails) {
        Optional<Film> optionalFilm = filmRepository.findById(id);

        if (optionalFilm.isPresent()) {
            Film film = optionalFilm.get();
            film.setTitre(filmDetails.getTitre());
            film.setGenre(filmDetails.getGenre());
            film.setDuree(filmDetails.getDuree());
            film.setRealisateur(filmDetails.getRealisateur());
            film.setDescription(filmDetails.getDescription());

            Film updatedFilm = filmRepository.save(film);
            return ResponseEntity.ok(updatedFilm);
        }

        return ResponseEntity.notFound().build();
    }

    // DELETE film
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        if (filmRepository.existsById(id)) {
            filmRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}