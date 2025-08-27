package com.example.payments.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;
import java.util.Random;

@RestController
public class PaymentsController {

  private final Random random = new Random();

  @GetMapping("/api/payments/charge")
  public Map<String, Object> charge(@RequestParam(defaultValue = "19.99") double amount) {
    // randomly fail to demonstrate circuit breaker
    boolean fail = random.nextDouble() < 0.25; // 25% fail
    if (fail) {
      throw new RuntimeException("Payment gateway timeout");
    }
    return Map.of(
        "status", "approved",
        "amount", amount,
        "ts", Instant.now().toString());
  }
}
