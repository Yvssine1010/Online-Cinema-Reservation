// Film.java (DTO pour recevoir les films)
package com.cinema.reservation.dto;

import lombok.Data;

@Data
public class Film {
    private Long id;
    private String titre;
    private String genre;
    private Integer duree;
    private String realisateur;
}