# hotel-reservation-system
A backend application for managing hotel reservations, users, and authentication.

## Technologies
- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- PostgreSQL
- JUnit
- Mockito
- MockMvc

## Current Features
- User registration
- User authentication with email and password
- JWT-based authentication
- Role-based users
- PostgreSQL integration
- Automated testing

## Configuration
The application uses PostgreSQL as its database.
Copy `application.properties.example` to:
```text
src/main/resources/application.properties
```
and configure the required database and JWT properties.

## Running the application
Make sure PostgreSQL is running and the database configured in `application.properties` exists.
Then run the application using Maven:
```bash
./mvnw spring-boot:run
```
Or run the application directly from IDE.

## Project Status
The project is currently under development. User registration and authentication are implemented, while the hotel reservation functionality is still being developed.
