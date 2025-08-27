package com.example.gateway.rate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;

@Configuration
public class UserKeyResolver {

  @Bean
  public KeyResolver userKeyResolver() {
    return exchange -> exchange.getPrincipal()
        .map(p -> (Authentication) p)
        .map(Authentication::getName)
        .switchIfEmpty(ipKey(exchange));
  }

  private Mono<String> ipKey(ServerWebExchange exchange) {
    var addr = exchange.getRequest().getRemoteAddress();
    String host = (addr != null) ? addr.getAddress().getHostAddress() : "unknown";
    return Mono.just("ip:" + host);
  }
}
