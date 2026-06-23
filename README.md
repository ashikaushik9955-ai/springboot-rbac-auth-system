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
* Dockerized deployment

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
* Docker

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



## Local Setup

### 1. Clone the repository

```bash
git clone https://github.com/ashikaushik9955-ai/springboot-rbac-auth-system.git
cd springboot-rbac-auth-system
```

### 2. Configure MySQL database

Create a MySQL database named:

```sql
student_platform
```

Update `application.properties` if needed:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_platform
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Run the project

```bash
mvn spring-boot:run
```

---

## Docker Setup

### 1. Build Docker image

```bash
docker build -t springboot-rbac-auth .
```

### 2. Run Docker container

```bash
docker run -d -p 8080:8080 --name rbac-test springboot-rbac-auth
```

### Note for Docker + Local MySQL

If you are running MySQL on your local machine (for example via XAMPP), the datasource URL may need to be changed to:

```properties
spring.datasource.url=jdbc:mysql://host.docker.internal:3306/student_platform
```

This allows the Docker container to connect to MySQL running on the host machine.



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
  

## Author


Ashi Kaushik
