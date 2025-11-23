// src/main/java/com/cinema/payment/controller/PaymentController.java
package com.cinema.payment.controller;

import com.cinema.payment.dto.PaiementRequest;
import com.cinema.payment.dto.PaiementResponse;
import com.cinema.payment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/test")
    public String test() {
        return "Payment service is working!";
    }

    @PostMapping("/process")
    public PaiementResponse processPayment(@RequestBody PaiementRequest request) {
        return paymentService.processPayment(request);
    }

    @GetMapping("/status/{reservationId}")
    public PaiementResponse getPaymentStatus(@PathVariable Long reservationId) {
        return paymentService.getPaymentStatus(reservationId);
    }
}