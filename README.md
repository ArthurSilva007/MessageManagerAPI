# Mensageria API

API de mensageria multi-instância com integração WhatsApp, construída com Spring Boot (WebFlux/R2DBC).

## Stack

- Java 21, Spring Boot 4 (WebFlux, R2DBC, Flyway, Security, AMQP)
- PostgreSQL, Redis, RabbitMQ, MinIO
- [WhatsappWeb4j](https://github.com/Auties00/WhatsappWeb4j) para conexão com o WhatsApp

## Configuração

As credenciais são lidas de variáveis de ambiente (veja `.env.example`). Para ambiente local:

```bash
cp .env.example .env
docker compose up -d
./mvnw spring-boot:run
```
