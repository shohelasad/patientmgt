# Patient Visit Manager


## Used Technologies

* Java 17
* Spring Boot 3.1.3
* Postgresql (for production level readiness)
* H2 Database for testing
* Spring Boot Validation
* Spring Boot JPA
* Lombok
* Flyway database migration
* Docker


# How to run

### Run only test cases

```sh
mvn test -Dspring.profiles.active=test
```

### Package the application as a JAR file

```sh
mvn clean install -DskipTests
```

### Run the Spring Boot application and PostgreSQL with Docker Compose
(for docker build change the database config postgresql in application.properties)

```sh
docker-compose up -d --build
```

## Testing Coverage

* Implement unit tests, integration tests with JUnit5 and mockito.
* Implement JaCoCo to measure code coverage and ensure that critical code paths are tested.

## Design patterns

* RESTful API Design Pattern: REST (Representational State Transfer) expose the endpoints restfully way
* Controller-Service-Repository Pattern:
  Controller: Receives incoming HTTP requests, handles request validation, and invokes the appropriate service methods.
  Service: Contains the business logic, including validation and processing, and interacts with the repository.
  Repository: Manages data storage and retrieval.
* DTO (Data Transfer Object) Pattern: Use DTOs to transfer data between your API and the client.
* Sorting and Pagination Patterns: For showing all patients as page list to select a patient from there
* Error Handling Patterns: Implement a consistent error-handling mechanism using Spring's exception handling. Return meaningful error responses in JSON format.
* Dependency Injection (DI) Pattern: Implement DI with constructor injection

## Production ready 

* Database PostgresSQL is configured for dockerige 
* Flyway in implement for data migration 

## API Definition

OpenAPI implemented for API definition 
* http://localhost:8080/api-docs 
* http://localhost:8080/swagger-ui/index.html


