package com.example.gateway.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class FallbackController {

  @GetMapping(value = "/fallback/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Map<String, Object>> usersFallback() {
    return Mono.just(Map.of("service", "users", "status", "fallback", "message", "Users service unavailable"));
  }

  @GetMapping(value = "/fallback/payments", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Map<String, Object>> paymentsFallback() {
    return Mono.just(Map.of("service", "payments", "status", "fallback", "message", "Payments service unavailable"));
  }
}
