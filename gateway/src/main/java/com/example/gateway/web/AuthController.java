package com.example.gateway.web;

import com.example.gateway.security.JwtService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final JwtService jwtService;

  public AuthController(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  public record LoginRequest(String username, List<String> roles) {}

  @PostMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, String> token(@RequestBody LoginRequest req) {
    String username = (req.username() == null || req.username().isBlank()) ? "user" : req.username();
    List<String> roles = (req.roles() == null || req.roles().isEmpty()) ? List.of("USER") : req.roles();
    String token = jwtService.createToken(username, roles);
    return Map.of("token", token);
  }
}
