# BookMyShow Backend + Frontend (bms)

A movie-ticket booking application, built as a backend-first project and now being extended with a frontend.

```
bms/
├── backend/     Spring Boot REST API (Java, MySQL)
└── frontend/    React + Vite + Tailwind CSS (in progress)
```

## Tech Stack

**Backend**
- Java 17, Spring Boot
- Spring Data JPA (Hibernate), MySQL
- Maven

**Frontend**
- React (Vite)
- Tailwind CSS

## Project Status

- Backend: feature-complete for movies, theaters, screens, shows, seats, bookings, payments, users, and login/forgot-password/reset-password.
- Frontend: in progress.

## Getting Started

### Prerequisites
- JDK 17+
- Maven
- MySQL running locally, with a database named `bms_db`
- Node.js + npm

### 1. Backend setup

```bash
cd backend
```

Update `src/main/resources/application.properties` with your local MySQL username/password if different from the defaults.

Run it:

```bash
mvn spring-boot:run
```

The API starts on `http://localhost:8080`. Tables are created automatically on first run (`spring.jpa.hibernate.ddl-auto=update`).

### 2. Frontend setup

```bash
cd frontend
npm install
npm run dev
```

The app starts on `http://localhost:5173` and calls the backend at `http://localhost:8080`.

Run both at the same time, in two separate terminals.

## API Overview

Base URL: `http://localhost:8080/api`

| Resource | Endpoints |
|---|---|
| Movies | `GET /movies`, `GET /movies/{id}`, `GET /movies/search?title=`, `GET /movies/language/{language}`, `GET /movies/genre/{genre}`, `POST /movies`, `PUT /movies/{id}`, `DELETE /movies/{id}` |
| Theaters | `GET /theaters`, `GET /theaters?city=`, `GET /theaters/{id}`, `POST /theaters` |
| Shows | `GET /shows`, `GET /shows/{id}`, `GET /shows/movie/{movieId}`, `GET /shows/movie/{movieId}/city/{city}`, `GET /shows/date?start=&end=`, `POST /shows` |
| Bookings | `GET /bookings/{id}`, `GET /bookings/number/{bookingNumber}`, `GET /bookings/user/{userId}`, `POST /bookings`, `PUT /bookings/{id}/cancel` |
| Users | `GET /users`, `GET /users/{id}`, `POST /users`, `POST /users/login`, `POST /users/forgot-password`, `POST /users/reset-password` |

Creating a show automatically generates its available seats from the screen's seat layout. Booking seats, cancelling, and password reset all follow validation rules described in code comments and `GlobalExceptionHandler`.

## Notes

- Passwords are stored in plain text — fine for this learning project, but not production-ready.
- No authentication tokens yet; the frontend tracks the logged-in user locally after `/users/login`.
- CORS is not yet configured on the backend; add it before the frontend calls a live backend from a different port, if not already done.

## Repo Structure Note

This repo was originally backend-only and was restructured into a monorepo (`backend/` + `frontend/`) to support frontend development.
