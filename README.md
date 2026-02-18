# Quesefy

Quesefy is an event management platform developed with a backend-first approach, powered by Spring Boot, with planned Web and Android clients.

This repository currently contains the backend REST API, focused on clean architecture, proper error handling, and automated testing.

## Project Structure

```
quesefy/
├── backend/   # Spring Boot REST API (active development)
├── android/   # Android client (planned)
└── web/       # Web client (planned)
```

## Tech Stack

### Backend

- Java
- Spring Boot (Web, Data JPA)
- Hibernate (JPA implementation)
- PostgreSQL
- Swagger / OpenAPI
- Docker Compose (local development)
- Maven
- JUnit 5 & Mockito

### Clients (planned)

- Android — Kotlin
- Web — TBD

## Architecture

The backend follows a layered architecture with clear separation of concerns:

- **Controller layer** — HTTP request/response handling
- **Service layer** — business logic
- **DTOs & mappers** — separation between API models and domain models
- **Repository layer** — Spring Data JPA
- **Global exception handling** — `@RestControllerAdvice`

## Backend Features

### Core Functionality

- Create and manage venues
- Create and manage events associated with venues
- Retrieve events by ID
- Update existing events and venues (partial updates supported)
- Assign and update event venues

### API & Validation

- Input validation using Bean Validation (`@Valid`)
- Meaningful HTTP responses for validation and business errors
- Centralized exception handling

## Running the Backend

### Prerequisites

Start the PostgreSQL database with Docker:

```bash
docker compose up -d
```

Default local configuration:

| Parameter | Value     |
|-----------|-----------|
| Database  | quesefy   |
| User      | quesefy   |
| Password  | quesefy   |
| Port      | 5432      |

### Start the application

From the `backend` directory:

```bash
./mvnw spring-boot:run
```

The application uses the `postgres` Spring profile by default. You can also set it explicitly:

```bash
SPRING_PROFILES_ACTIVE=postgres ./mvnw spring-boot:run
```

### Run tests

```bash
./mvnw test
```

## Project Status

The project is in an early stage and actively evolving, with a focus on building a solid backend foundation before expanding to client applications.
