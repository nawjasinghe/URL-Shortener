# URL Shortener

A Spring Boot web app that takes long URLs and generates unique short links. The application then stores shortened URLs in a PostgreSQL database running in Docker and displays them in a table on a localhost website

## Features

- **Full CRUD Operations:** Create, Read, Update, and Delete shortened URLs
- Shorten any URL by entering it in a web form
- Automatically generates a unique 6-character short key for each URL
- Stores all shortened URLs in a PostgreSQL database
- Displays all shortened URLs in a clean, organized table
- Clickable short links that redirect to the original URLs
- URL normalization (automatically adds https:// if missing)
- URL validation (checks format and domain structure)
- Duplicate detection to avoid creating multiple entries for the same URL
- Update existing shortened URLs with new destinations
- Delete shortened URLs when no longer needed

## Tech Stack

- **Backend:** Spring Boot 4.0.1
- **Language:** Java 21
- **Database:** PostgreSQL 16 (Docker)
- **ORM:** Spring Data JPA with Hibernate
- **Template Engine:** Thymeleaf
- **Frontend:** HTML, CSS, Bootstrap 5.3.3
- **Build Tool:** Maven

## Prerequisites

- Java 21 or higher
- Maven
- Docker Desktop

## Getting Started

### 1. Start the PostgreSQL Database

Run the following command from the project root directory with the latest version of docker desktop running:

```bash
docker compose up -d
```

This starts a PostgreSQL container with:
- Port: 5432
- Database: postgres
- Username: postgres
- Password: postgres

### 2. Build and Run the Application

Using Maven:

```bash
./mvnw spring-boot:run
```

Or on Windows:

```bash
mvnw.cmd spring-boot:run
```

### 3. Access the Application

Open your browser and go to:

```
http://localhost:8080
```

## Usage

### Create a Shortened URL
1. Enter a URL in the input field (e.g., `youtube.com` or `https://www.google.com`)
2. Click the "Shorten" button
3. The shortened URL will appear in the table below
4. Click on the short link to be redirected to the original URL

### Update a Shortened URL
1. Find the URL you want to update in the table
2. Enter the new destination URL in the input field next to it
3. Click the "Update" button

### Delete a Shortened URL
1. Find the URL you want to delete in the table
2. Click the "Delete" button next to it
3. The URL will be removed from the database

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Home page displaying all shortened URLs |
| POST | `/shorten` | Create a new shortened URL |
| POST | `/update/{shortKey}` | Update an existing shortened URL |
| POST | `/delete/{shortKey}` | Delete a shortened URL |
| GET | `/{shortKey}` | Redirect to the original URL |


```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

## Database Schema

The `shortened_urls` table contains:

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key (auto-generated) |
| original_url | VARCHAR | The original long URL |
| short_key | VARCHAR | The unique 6-character short key |
| is_private | BOOLEAN | Whether the URL is private (Work in Progress)| 
| created_at | TIMESTAMP | When the URL was created |
| expires_at | TIMESTAMP | Expiration date (optional) (Work in Progress) | 
| click_count | BIGINT | Number of times the link was clicked (Work in Progress) | 

## How It Works

1. User submits a URL through the web form
2. The URL is normalized (https:// is added if missing)
3. A unique short key is generated using the URL's hash code
4. The system checks for duplicate keys to avoid collisions
5. The shortened URL is saved to the PostgreSQL database
6. The user is redirected to the home page to see the updated list

## Stopping the Application

To stop the PostgreSQL container:

```bash
docker compose down
```

