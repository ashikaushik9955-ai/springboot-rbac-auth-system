# Spring Boot JWT RBAC Authentication System

A secure Role-Based Authentication & Authorization system built using **Spring Boot**, **Spring Security**, **JWT Authentication**, and **MySQL**.

This project demonstrates secure login, JWT token generation, role-based access control (RBAC), and an admin-controlled teacher approval workflow.

## Features

* User Registration (Student / Teacher)
* Secure Login using JWT Authentication
* Role-Based Authorization (RBAC)
* BCrypt Password Encryption
* Admin Dashboard Access
* Student Dashboard Access
* Teacher Dashboard Access
* Teacher Approval Workflow (Admin-controlled)
* Protected APIs using JWT Filter
* Stateless Authentication using Spring Security
* Swagger API Documentation

## Tech Stack

* Java
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* Swagger OpenAPI
* Git & GitHub

API Documentation
Swagger UI



## Project Workflow

### 1. User Registration

Users can register as:

* STUDENT

* TEACHER

* Students are verified by default.

* Teachers require admin approval.

### 2. Login Authentication

After successful login:

* JWT Token is generated
* Email and Role are stored inside the token claims
* Token expiration is configured for security

### 3. Authorization

Role-based authorization is implemented:

* STUDENT → Student Dashboard
* TEACHER → Teacher Dashboard
* ADMIN → Admin Dashboard

Unauthorized access is restricted using Spring Security.

### 4. Teacher Approval Workflow

Teacher accounts remain inactive until approved by the Admin.

Admin can approve teachers using:

PUT /auth/admin/approve-teacher/{id}

## API Endpoints

### Authentication APIs

POST /auth/register
POST /auth/login

### Role-Based APIs

GET /auth/student
GET /auth/teacher
GET /auth/admin

### Admin APIs

PUT /auth/admin/approve-teacher/{id}

## Security Features

* JWT Token Validation
* Bearer Token Authentication
* Role-Based Authorization
* Password Encryption using BCrypt
* Stateless Session Management

## Future Improvements

* Refresh Token Implementation
* Email Verification
* Forgot Password Feature
* Docker Deployment

## Author
ASHI KAUSHIK

Ashi Kaushik
