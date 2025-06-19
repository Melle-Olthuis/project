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

Once both servers are running, open the application in your browser. The registration page has been removed, so make sure to create users directly in the database. Passwords must be stored using BCrypt hashing. You can generate a hash using a small Spring snippet with `BCryptPasswordEncoder` and insert the resulting value into the `user` table before logging in.

