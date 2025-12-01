// Salle.java (DTO pour recevoir les salles)
package com.cinema.reservation.dto;

import lombok.Data;

@Data
public class Salle {
    private Long id;
    private String nom;
    private Integer capacite;

    public Salle(Long id, String nom, Integer capacite) {
        this.id = id;
        this.nom = nom;
        this.capacite = capacite;
    }
    public Salle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }
}