// src/main/java/com/cinema/reservation/client/PaymentServiceClient.java
package com.cinema.reservation.client;

import com.cinema.reservation.dto.PaiementRequest;
import com.cinema.reservation.dto.PaiementResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {

    @PostMapping("/api/payments/process")
    PaiementResponse processPayment(@RequestBody PaiementRequest request);

    @GetMapping("/api/payments/status/{reservationId}")
    PaiementResponse getPaymentStatus(@PathVariable Long reservationId);
}