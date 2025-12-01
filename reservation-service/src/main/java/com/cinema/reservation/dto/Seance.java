// Seance.java - AJOUTE LE CHAMP MANQUANT
package com.cinema.reservation.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class Seance {
    private Long id;
    private Film film;
    private Salle salle;
    private LocalDateTime horaire;
    private Integer placesDisponibles;  // 👈 CHAMP AJOUTÉ
    private BigDecimal prix;

    // Getters/Setters si Lombok ne marche pas
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Film getFilm() { return film; }
    public void setFilm(Film film) { this.film = film; }

    public Salle getSalle() { return salle; }
    public void setSalle(Salle salle) { this.salle = salle; }

    public LocalDateTime getHoraire() { return horaire; }
    public void setHoraire(LocalDateTime horaire) { this.horaire = horaire; }

    public Integer getPlacesDisponibles() { return placesDisponibles; }  // 👈 GETTER
    public void setPlacesDisponibles(Integer placesDisponibles) { this.placesDisponibles = placesDisponibles; }

    public BigDecimal getPrix() { return prix; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }
}