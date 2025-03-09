# Tic-Tac-Toe Project

## Design Choices

1. **Database**:
    - Used **MySQL (version 9.2)** as the database. While NoSQL databases could have been an option, MySQL was chosen for faster development.

2. **Containerization**:
    - **Docker Compose** is used to run the MySQL service.
    - A `docker-compose.yml` file is included in the root of the project.
    - The Docker container automatically creates the `tiktaktoe` database.

3. **Schema Management**:
    - **Flyway** is used to automate schema creation after the application starts.

4. **Technology Stack**:
    - The application is developed using **Spring Boot (version 3.4.2)** and **Java (version 21)**.

## Future Enhancements

1. **Containerization**:
    - Create a Docker image for the service.
    - Configure **Kubernetes Helm charts**.
    - Use **Helmfile** for environment-specific configurations.
    - The exact stack depends on production requirements.

2. **CI/CD Pipeline**:
    - Implement a CI/CD pipeline using **GitHub Actions** or **Jenkins** for automated builds and deployments.

3. **Monitoring & Metrics**:
    - Integrate **Spring Boot Actuator** to monitor the application's health and collect performance metrics.

4. **Authentication & Authorization**:
    - Implement authentication and authorization for securing API endpoints.

5. **Test Coverage**:
    - Add **integration tests**.
    - Support **performance testing**.

6. **API Documentation**:
    - Provide detailed API documentation to ensure clarity and ease of use for developers.

## Usage

### Request Examples

#### Play a Move (Specific Game ID)
```bash
curl --location 'http://localhost:8080/game/play/5' \
--header 'Content-Type: application/json' \
--data '{
    "player": "X",
    "row": 2,
    "column": 1
}'
```

#### Play a Move (New Game)
```bash
curl --location 'http://localhost:8080/game/play' \
--header 'Content-Type: application/json' \
--data '{
    "player": "X",
    "row": 2,
    "column": 1
}'
```

### Response Example
```json
{
  "id": 1,
  "lastPlayer": "O",
  "winner": null,
  "created": "2025-03-09T11:18:06.284+00:00",
  "lastUpdated": "2025-03-09T11:18:06.285+00:00",
  "gameStatus": [
    [
      "O",
      "O",
      null
    ],
    [
      "X",
      null,
      null
    ],
    [
      null,
      null,
      null
    ]
  ],
  "ended": false
}
```

