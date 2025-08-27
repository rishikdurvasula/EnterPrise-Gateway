package com.example.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
public class UserController {

  @GetMapping("/api/users/profile")
  public Map<String, Object> profile(@RequestHeader(name="X-Request-ID", required=false) String reqId) {
    return Map.of(
        "user", Map.of("id", 1, "name", "Jane Doe", "plan", "pro"),
        "requestId", reqId == null ? "n/a" : reqId,
        "ts", Instant.now().toString()
    );
  }

  @GetMapping("/api/users/delay")
  public Map<String, Object> delayed() throws InterruptedException {
    Thread.sleep(3000);
    return Map.of("status", "ok", "message", "Delayed response", "ts", Instant.now().toString());
  }
}
