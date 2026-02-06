# Quesefy

Quesefy is an event management platform developed with a backend-first approach,
powered by Spring Boot, with planned Web and Android clients.

This repository currently contains the backend REST API, which focuses on
clean architecture, proper error handling, and automated testing.

## Project Structure
- backend: Spring Boot REST API (active development)
- android: Android client (planned)
- web: Web client (planned)

## Backend Features

### Core Functionality
- Create and manage venues
- Create and manage events associated with venues
- Retrieve events by ID
- Update existing events and venues (supports partial updates)
- Assign and update event venues

### API & Validation
- Input validation using Bean Validation (`@Valid`)
- Meaningful HTTP responses for validation and business errors
- Centralized exception handling

## Tech Stack

### Backend
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate (JPA implementation)
- Swagger / OpenAPI
- H2 (in-memory database for development)
- PostgreSQL (planned)
- Maven
- JUnit 5 & Mockito

### Clients (planned)
- Android (Kotlin)
- Web (TBD)

## Architecture
The backend follows a layered architecture with clear separation of concerns:

- Controller layer for HTTP request/response handling
- Service layer for business logic
- DTOs and mappers to separate API models from domain models
- Repository layer using Spring Data JPA
- Global exception handling using `@RestControllerAdvice`
  
## Running the backend
bash
./mvnw spring-boot:run

## Running tests
./mvnw test

## Project Status

The project is in an early stage and actively evolving, with a focus on
building a solid backend foundation before expanding to client applications.
