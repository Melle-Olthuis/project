# Project Setup

This repository contains a Spring Boot back‑end and a Vue front‑end. To use the login functionality, a MySQL database is required.

## Database

A `docker-compose.yml` file has been added for convenience. It starts a MySQL 8 server with an empty root password and automatically creates a database named `notedb`.

Start the database with:

```bash
docker compose up -d
```

The back‑end connects to `localhost:3306` using the `notedb` database. Flyway migrations in `Backend/src/main/resources/db/migration` will be executed on startup to create the necessary tables.

## Running the Back‑end

With the database running, you can start the Spring Boot application:

```bash
cd Backend
./mvnw spring-boot:run
```

## Running the Front‑end

Install dependencies and launch the development server:

```bash
cd Frontend
npm install
npm run dev
```

Once both servers are running, open the application in your browser and use the login page to create an account and sign in.

