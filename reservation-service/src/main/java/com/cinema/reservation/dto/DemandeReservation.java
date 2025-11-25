
package com.cinema.reservation.dto;

import lombok.Data;

@Data
public class DemandeReservation {
    private Long seanceId;
    private Long utilisateurId;
    private Integer nombrePlaces;
    private String email;
}