# Enterprise API Gateway (Spring Boot + Spring Cloud Gateway)

A production-style **API Gateway** with JWT auth, per-user **rate limiting** via Redis,
**circuit breakers** with Resilience4j, centralized **metrics** (Prometheus) and a
lightweight dashboard. Includes two mock downstream services.

## What’s inside
- **gateway/**: Spring Cloud Gateway (WebFlux + Security + Actuator + Prometheus)
- **users-service/** and **payments-service/**: mock microservices
- **Redis** for rate limiting, **Prometheus** for metrics, **Grafana** for dashboards
- **Docker Compose** for one-command local orchestration

## Quick start
```bash
# 1) Build all modules
mvn -DskipTests package

# 2) Run with Docker Compose (gateway, services, redis, prometheus, grafana)
docker compose up --build
```

- Gateway: http://localhost:8080
- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000 (admin / admin)
- Users service (via gateway): http://localhost:8080/api/users/profile
- Payments service (via gateway): http://localhost:8080/api/payments/charge

## Auth
Get a demo token:
```bash
curl -X POST http://localhost:8080/auth/token -H "Content-Type: application/json"   -d '{"username":"rishik","roles":["USER"]}'
```
Use the returned token:
```bash
curl http://localhost:8080/api/users/profile -H "Authorization: Bearer <TOKEN>"
```

## Rate limiting
Per-principal (username) limiting via Redis. If not authenticated, falls back to IP-based key.

## Circuit breaker
Route-level circuit breakers with fallback endpoints:
- `/api/users/**` -> fallback `/fallback/users`
- `/api/payments/**` -> fallback `/fallback/payments`

## Notes
- Java 17, Spring Boot 3.2.x, Spring Cloud 2023.0.x
- You can tune rate limits in `gateway/src/main/resources/application.yml`.
