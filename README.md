# todo-service

## 1. Description

The `todo-service` is a simple application designed to help users manage their tasks. It allows users to
create, read, update, and delete tasks.

## 2. Setup

### 2.1 Prerequisites

- Docker
- Kotlin/Java

### 2.2 Running the application

1. Clone the repository.
2. Create a `.env` file in the project's root directory and set the required environment variables (see `.env.example`).
3. Run the following command in the project's terminal to start the database:
```
docker compose up
```
4. Create a `flyway` user in the database and grant it the required privileges:
```
CREATE USER flyway WITH ENCRYPTED PASSWORD '<password>';
GRANT ALL PRIVILEGES ON SCHEMA public TO flyway;
```
5. Create a `service` user in the database and grant it the required privileges:
```
CREATE USER service WITH ENCRYPTED PASSWORD '<password>';
ALTER DEFAULT PRIVILEGES 
    FOR USER flyway
    IN SCHEMA public
    GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO service;
```
6. Run the following command in the project's terminal to build the application:
```
./mvnw spring-boot:run
```
7. The application will be accessible at `http://localhost:8080/swagger-ui/index.html`.
8. To stop the database and application, hit ```Ctrl + C``` in the terminal windows.
