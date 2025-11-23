// src/main/java/com/cinema/film/model/Salle.java
package com.cinema.film.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private Integer capacite; // nombre de places

    private String equipements; // "3D, 4DX, IMAX"
}