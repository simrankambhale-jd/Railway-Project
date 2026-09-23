IRCTC Practice Backend

A backend application for an IRCTC-style railway ticket booking system built using Java, Spring Boot, Spring Security, JWT, JPA/Hibernate, and MySQL.

The project provides separate functionality for Normal Users and Administrators, including train search, station management, train schedules, coach and seat management, ticket booking, and ticket cancellation.

🚀 Technologies Used
Java 21
Spring Boot 4.1.1
Spring Web
Spring Security
JWT Authentication
Spring Data JPA
Hibernate
MySQL 8
Maven
Postman
IntelliJ IDEA
HTML, CSS and JavaScript for the basic frontend
📌 Main Features
Authentication & Authorization
User registration
User login
JWT-based authentication
Access token and refresh token
Password encryption using BCrypt
Role-based authorization
Separate Admin and Normal User access
Admin Features

Administrators can:

Add, update and delete stations
Add and manage trains
Add train routes
Create train schedules
Create and manage train coaches
Create and manage train seats
View and manage railway data
User Features

Normal users can:

View available stations
Search trains between two stations
Select a journey date
View available coaches
View available seats
Select seats
Enter passenger details
Book tickets
View their bookings
Cancel tickets

When a ticket is cancelled, the booking remains in the database with a cancelled status while the booked seats are made available again.

🏗️ Project Structure
src/main/java/practice/irctc/IRCTC
│
├── Config
│   ├── CorsConfig.java
│   ├── ProjectConfig.java
│   │
│   └── Security
│       ├── CustomUserDetail.java
│       ├── CustomUserDetailService.java
│       ├── JwtAuthenticationEntryPoint.java
│       ├── JwtAuthenticationFilter.java
│       ├── JwtHelper.java
│       └── SecurityConfig.java
│
├── Controllers
│   ├── AuthController.java
│   ├── StationController.java
│   ├── TrainController.java
│   ├── TrainRouteController.java
│   ├── TrainScheduleController.java
│   ├── TrainCoachController.java
│   ├── TrainSeatController.java
│   │
│   └── User
│       ├── UserTrainController.java
│       ├── UserStationController.java
│       └── BookingController.java
│
├── DTO
│   ├── UserDto.java
│   ├── TrainDto.java
│   ├── TrainRouteDto.java
│   ├── TrainScheduleDto.java
│   ├── TrainCoachDto.java
│   ├── TrainSeatDto.java
│   ├── StationsDto.java
│   ├── CoachAvailabilityDto.java
│   ├── AvailableTrainResponse.java
│   └── Booking
│       ├── BookingRequest.java
│       ├── BookingDto.java
│       ├── BookingResponse.java
│       ├── PassengerDto.java
│       ├── TicketResponse.java
│       └── TicketPassenger.java
│
├── Entity
│   ├── User.java
│   ├── Role.java
│   ├── Stations.java
│   ├── Train.java
│   ├── TrainRoute.java
│   ├── TrainSchedule.java
│   ├── TrainCoach.java
│   ├── TrainSeat.java
│   ├── Booking.java
│   ├── BookedSeat.java
│   ├── Passenger.java
│   ├── CoachType.java
│   ├── BerthType.java
│   └── BookingStatus.java
│
├── Repository
│
├── Services
│
├── Services/Impl
│
└── Exceptions

🔐 Authentication

The application uses JWT for authentication.

Login
POST /auth/login

Example request:

{
  "email": "user@gmail.com",
  "password": "password"
}

The response contains an access token and refresh token.

For protected APIs, send:

Authorization: Bearer <access-token>
Token validity
Access token: 10 minutes
Refresh token: 60 minutes

🌐 Basic Frontend

A basic HTML/CSS/JavaScript frontend is included in the project.

Main pages:

index.html
login.html
register.html
dashboard.html
admin.html

JavaScript files:

js/api.js
js/login.js
js/register.js
js/user.js
js/admin.js

The frontend communicates with the Spring Boot backend using REST APIs and JWT authentication.

