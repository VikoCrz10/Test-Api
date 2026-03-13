# User API - Technical Test

## Overview

This project is a REST API developed with **Java and Spring Boot** that manages users and their addresses.
The API allows creating, retrieving, updating, deleting, and authenticating users.

User passwords are encrypted using **AES-256** before being stored.

The application uses an **in-memory list** to store users, as required by the technical test instructions.

---

# Technologies Used

* Java 17+
* Spring Boot
* Maven
* AES-256 Encryption
* RESTful API
* Git

---

# Project Structure

```
src
 ├── controller
 │     └── UserController.java
 │
 ├── service
 │     └── UserService.java
 │
 ├── model
 │     ├── User.java
 │     └── Address.java
 │
 ├── util
 │     └── AESUtil.java
 │
 └── UserApiApplication.java
```

---

# Features Implemented

### User Management

* Create new users
* Retrieve all users
* Retrieve users sorted by different attributes
* Update users (PATCH)
* Delete users

### Authentication

* Login endpoint
* Password encryption using AES-256

### Address Management

* Users can have multiple addresses

---

# Endpoints

## Get all users

```
GET /users
```

Optional sorting:

```
GET /users?sortedBy=email
GET /users?sortedBy=name
GET /users?sortedBy=id
GET /users?sortedBy=phone
GET /users?sortedBy=tax_id
GET /users?sortedBy=created_at
```

---

## Create User

```
POST /users
```

Example request:

```json
{
  "email": "user@mail.com",
  "name": "User Example",
  "phone": "+52 5555555555",
  "password": "123456",
  "taxId": "AARR990101XXX"
}
```

---

## Login

```
POST /login
```

Example request:

```json
{
  "email": "user@mail.com",
  "password": "123456"
}
```

---

## Update User

```
PATCH /users/{id}
```

Example request:

```json
{
  "name": "Updated Name",
  "phone": "+52 5555550000"
}
```

---

## Delete User

```
DELETE /users/{id}
```

---

# Password Security

Passwords are encrypted using **AES-256** before being stored in memory.

The password field is configured as:

```
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
```

This allows the API to receive the password but prevents it from being returned in responses.

---

# How to Run the Project

1. Clone the repository

```
git clone <repository-url>
```

2. Navigate to the project directory

```
cd user-api
```

3. Run the application

```
mvn spring-boot:run
```

The API will start at:

```
http://localhost:8080
```

---

# Notes

* The application uses an **in-memory data structure** to store users as required by the technical test.
* No external database is used.


---

# Author

Technical test developed by:

**Víctor Cruz**
