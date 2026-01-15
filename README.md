# Quesfy

Quesfy is an event management platform with a Spring Boot backend and
future Web and Android clients.

This repository currently contains the backend REST API, which focuses on
clean architecture, proper error handling and automated testing.

## Project Structure
- backend: REST API built with Spring Boot
- android: Android client (planned)
- web: Web client (planned)

## Backend Features
- Create events
- Update existing events
- Retrieve events by ID
- Input validation with meaningful HTTP responses
- Centralized exception handling

## Tech Stack
### Backend
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- H2 (in-memory database)
- Maven
- JUnit 5 & Mockito

### Clients (planned)
- Android (Kotlin)
- Web (TBD)

## Architecture
The backend follows a layered architecture:
- Controller layer for HTTP handling
- Service layer for business logic
- DTOs and mappers for API/domain separation
- Global exception handling using `@RestControllerAdvice`

## Running the backend
bash
./mvnw spring-boot:run

## Running tests
./mvnw test

## Project Status

The project is in an early stage and actively evolving.
