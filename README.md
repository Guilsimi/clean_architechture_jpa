[Ler em português](README-pt_br.md)

## 🇺🇸 English
# JPA with Clean Architecture

___
This project aims to demonstrate the implementation of **JPA** (_Java Persistence API_) following the principles of **Clean Architecture**.<br>
**Clean Architecture** promotes **separation of concerns** in code using **layers**, facilitating maintenance, scalability, and testability of the system, protecting and isolating the application's **business rules** from external details.<br>
The **JPA** library is used to manage data persistence in Java applications, allowing mapping between Java objects and relational database tables. It was chosen for being a widely used library in Java projects.

## Tecnologies

---
![Java-21](https://img.shields.io/badge/Java-21-gray.svg?style=for-the-badge&logo=openjdk&logoColor=&labelColor=red)
![Spring-3.5.7](https://img.shields.io/badge/spring-3.5.7-gray.svg?style=for-the-badge&logo=spring&logoColor=white&labelColor=%236DB33F)
<br><br>

## Table of Contents

---
- [Installation](#installation)<br>
- [Execution](#execution)<br>
- [Endpoints](#endpoints)<br>
  <br>

## Installation

---

1. Clone the repository:
   ```bash
   git clone https://github.com/Guilsimi/clean_architechture_jpa.git
    ```
2. Access the project directory:
   ```bash
   cd {directory_path}/clean_architechture_jpa
   ```
3. Install the project dependencies using Maven.
   ```bash
   mvn clean install
   ```
<br>

## Execution

---
1. Run the application using Maven.
   ```bash
   mvn spring-boot:run
   ```
2. The application will be available at `http://localhost:8080`.<br><br>
3. The H2 database can be accessed at `http://localhost:8080/h2-console`.<br>
    - JDBC URL: `jdbc:h2:mem:testdb`<br>
    - Username: `sa`<br>
    - Password: `password`<br>

<br>

## Endpoints

---

### **Create User**
- **URL:** `/users`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "firstName": "{ User's First Name }",
    "lastName": "{ User's Last Name }",
    "email": "{ User's Email } ",
    "password": "{ User's Password } "
  }
  ```
- **Response:**
  ```http
  201 Created
  ```
<br>

### **Get User by Email**
- **URL:** `/users/get?email={email}`
- **Method:** `GET`
  <br><br>
- **Response:**
  ```http
  200 OK
  ```
  ```json
  {
    "firstName": "{ User's First Name }",
    "lastName": "{ User's Last Name }",
    "email": "{ User's Email } "
  }
  ```
<br>

### **Update User**
- **URL:** `/users/update/{id}`
- **Method:** `PUT`
- **Request Body:**
  ```json
  {
    "firstName": "{ User's New First Name }",
    "lastName": "{ User's New Last Name }",
    "password": "{ User's New Password } "
  }
  ```
- **Response:**
  ```http
  204 No Content
  ```
<br>

### **Delete User by Id**
- **URL:** `/users/delete/{id}`
- **Method:** `DELETE`
  <br><br>
- **Response:**
  ```http
  204 NO CONTENT
  ```