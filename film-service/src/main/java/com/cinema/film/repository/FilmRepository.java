// src/main/java/com/cinema/film/repository/FilmRepository.java
package com.cinema.film.repository;

import com.cinema.film.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    // Pas besoin d'annotations REST
}