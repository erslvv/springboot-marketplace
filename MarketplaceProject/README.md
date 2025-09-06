# 🛒 Marketplace API

Pet-проект: REST API для простого маркетплейса (товары, категории, пользователи, корзина и заказы).

---

## 🚀 Стек технологий

- **Java 21**, **Spring Boot 3.5**
- Spring Web, Spring Data JPA, Spring Validation
- PostgreSQL + Flyway (миграции БД)
- Spring Security (JWT)
- MapStruct, Lombok
- Springdoc OpenAPI (Swagger UI)
- Testcontainers, JUnit 5
- Docker & docker-compose
- GitHub Actions (CI/CD)

---
## 🧱 Архитектура слоёв
controller   →   facade   →   service   →   repository

DTO ↔ MapStruct ↔ Entity
## 📦 Запуск проекта

### Локально
```bash
# 1. Поднять базу
docker compose up -d db

# 2. Запустить приложение
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

## После запуска
Swagger UI → http://localhost:8080/swagger-ui/index.html
