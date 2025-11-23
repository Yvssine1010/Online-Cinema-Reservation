package com.cinema.payment.repository;

import com.cinema.payment.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    Optional<Paiement> findByReservationId(Long reservationId);
}