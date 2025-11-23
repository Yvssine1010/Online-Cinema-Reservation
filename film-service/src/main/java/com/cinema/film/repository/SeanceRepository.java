package com.cinema.film.repository;

import com.cinema.film.model.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeanceRepository extends JpaRepository<Seance, Long> {
    List<Seance> findByFilmId(Long filmId);
}