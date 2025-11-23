// src/main/java/com/cinema/film/repository/SalleRepository.java
package com.cinema.film.repository;

import com.cinema.film.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalleRepository extends JpaRepository<Salle, Long> {
}