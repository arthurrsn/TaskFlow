# TaskFlow API V1.0

![Status: Completed](https://img.shields.io/badge/Status-Completed-brightgreen)

![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![REST API](https://img.shields.io/badge/REST_API-000000?style=for-the-badge&logo=rest&logoColor=white)

---

## 📝 Project Description

This project is a **complete RESTful API** developed with **Spring Boot** for task management. The **TaskFlow API** allows users to create, view, update, and delete their activities persistently in a relational database. The goal was to build a robust and scalable solution, applying Back-end development best practices.

---

## ✨ Features

The **TaskFlow API** offers the following main functionalities for task management:

* **Task Management (Full CRUD):**
    * **Create Task (`POST`):** Register new tasks with title, description, status, and priority.
    * **List All Tasks (`GET`):** Retrieve a list of all registered tasks.
    * **Search Task by Term (`GET`):** Search for tasks by keywords in their title or description.
    * **Update Task (`PUT`):** Modify information of an existing task by its ID.
    * **Delete Task (`DELETE`):** Remove a task from the system by its ID.
* **Data Persistence:** Secure storage of tasks in a MySQL database via Spring Data JPA.
* **Data Validation:** Implementation of robust input validations in the API to ensure the integrity of received information (ex: mandatory fields, status/priority formats).
* **Standardized Responses:** Use of `ResponseEntity` to control and standardize HTTP responses (status codes, headers).

---

## 🚀 Technologies Used

* **Languages:** Java 21+, SQL
* **Frameworks:**
    * Spring Boot 3.5.3 (Spring Web, Spring Data JPA, Validation)
    * Lombok
* **Database:** MySQL
* **ORM:** JPA (Java Persistence API) with Hibernate
* **Tools:** Maven, Git, Postman/Insomnia (for API testing)
* **Concepts:** RESTful APIs, Layered Architecture, Object-Oriented Programming (OOP), DTOs (Data Transfer Objects), Bean Validation

---

## 🏗️ Architecture

The project follows a **layered architecture (Layered Architecture)**, promoting the separation of concerns and code maintainability:

* **`Controller`**: Presentation layer. Responsible for mapping HTTP endpoints, receiving request data, and delegating logic to the service layer.
* **`Service`**: Business logic layer. Contains business rules, validations, and orchestrates operations, interacting with the repository.
* **`Repository`**: Data access layer. Responsible for direct communication with the database, using Spring Data JPA for CRUD operations.
* **`Model` (Entities)**: Represents domain entities and their mapping to database tables (Ex: `Task`).
* **`DTO` (Data Transfer Objects)**: Objects used for data transfer between API layers and the client, ensuring flexibility and control over information exposure.

---

## ▶️ How to Run the Project

Follow the steps below to configure and run the **TaskFlow API** locally:

1.  **Prerequisites:**
    * Java Development Kit (JDK) 21+
    * Apache Maven 3.8+
    * MySQL Server 8.0+
    * MySQL Workbench (or another MySQL client for database management)

2.  **Database Configuration:**
    * Create a schema (banco de dados) in your MySQL Server with the name `taskflow`.
    * Create a user and password for the database (or utilize `root` with your password, para ambiente de desenvolvimento).
    * **Update the `src/main/resources/application.properties` file** with your database credentials:
        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/taskflow?useSSL=false&serverTimezone=UTC
        spring.datasource.username=seu_usuario_mysql
        spring.datasource.password=sua_senha_mysql
        spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver

        spring.jpa.hibernate.ddl-auto=update
        spring.jpa.show-sql=true
        spring.jpa.properties.hibernate.format_sql=true
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
        ```
    * Spring Data JPA will automatically create the `task` table on the first execution of the application.

3.  **Executing the Application:**
    * **Via IDE (IntelliJ IDEA, Eclipse):**
        * Import the project as a Maven project.
        * Navigate to the main class `br.projeto.taskflow.TaskflowApplication.java`.
        * Execute the `main` method.
    * **Via Linha de Comando (Maven):**
        * Open the terminal in the project root.
        * Execute: `mvn clean install` (to build the project and download dependencies)
        * Execute: `mvn spring-boot:run` (to start the application)

4.  **API Access:**
    * The API will be available at `http://localhost:8080/tasks`.

---

## 🧪 API Endpoints

The backend application exposes the following REST endpoints for the `Task` resource:

### **`POST /tasks` - Create a new task**

* **Description:** Registers a new task in the system, persisting it in the database.
* **Request Body (JSON - `TaskRequest`):**
    ```json
    {
      "title": "Estudar Spring Boot",
      "description": "Revisar conceitos de JPA e Security",
      "status": "PENDING",
      "priority": "HIGH"
    }
    ```
* **Response (JSON - `TaskResponse`):** Returns the created `Task` object, including the `id` generated by the database.
    ```json
    {
      "id": 1,
      "title": "Estudar Spring Boot",
      "description": "Revisar conceitos de JPA e Security",
      "status": "PENDING",
      "priority": "HIGH",
      "createdAt": "2025-07-22T10:00:00"
    }
    ```

### **`GET /tasks` - List all tasks**

* **Description:** Returns a list of all registered tasks from the database.
* **Response (JSON - `List<TaskResponse>`):**
    ```json
    [
      {
        "id": 1,
        "title": "Estudar Spring Boot",
        "description": "Revisar conceitos de JPA e Security",
        "status": "PENDING",
        "priority": "HIGH",
        "createdAt": "2025-07-22T10:00:00"
      },
      {
        "id": 2,
        "title": "Fazer compras",
        "description": "Lista de supermercado",
        "status": "PENDING",
        "priority": "LOW",
        "createdAt": "2025-07-22T11:00:00"
      }
    ]
    ```

### **`GET /tasks/{search}` - Search task by term**

* **Description:** Returns tasks whose title or description contains the search term.
* **Path Parameters:** `search` (String) - Search term.
* **Example URL:** `http://localhost:8080/tasks/estudar`
* **Response (JSON - `List<TaskResponse>`):**
    ```json
    [
      {
        "id": 1,
        "title": "Estudar Spring Boot",
        "description": "Revisar conceitos de JPA e Security",
        "status": "PENDING",
        "priority": "HIGH",
        "createdAt": "2025-07-22T10:00:00"
      }
    ]
    ```

### **`PUT /tasks/{id}` - Update an existing task**

* **Description:** Updates all data for a specific task by its ID.
* **Path Parameters:** `id` (Long) - Task ID.
* **Request Body (JSON - `TaskRequest`):** (All fields for complete replacement)
    ```json
    {
      "title": "Estudar Spring Boot Avançado",
      "description": "Aprofundar em Spring Security",
      "status": "IN_PROGRESS",
      "priority": "URGENT"
    }
    ```
* **Response (JSON - `TaskResponse`):** Retorna a tarefa atualizada.
    ```json
    {
      "id": 1,
      "title": "Estudar Spring Boot Avançado",
      "description": "Aprofundar em Spring Security",
      "status": "IN_PROGRESS",
      "priority": "URGENT",
      "createdAt": "2025-07-22T10:00:00"
    }
    ```

### **`DELETE /tasks/{id}` - Delete a task**

* **Description:** Removes a task from the system by its identifier.
* **Path Parameters:** `id` (Long) - Task ID.
* **Example URL:** `http://localhost:8080/tasks/1`
* **Response:** HTTP Status `204 No Content` (if deletion is successful).

---

## 🤝 Contribution

If you wish to contribute to this project, feel free to:
1.  Fork the repository.
2.  Create a new branch (`git checkout -b feature/your-feature`).
3.  Make your changes and commits.
4.  Push your changes to your fork (`git push origin feature/your-feature`).
5.  Open a Pull Request to the `main` branch of this repository.

---

## 📧 Contact

If you have any questions, suggestions, or want to exchange ideas, feel free to contact me:

* **LinkedIn:** [Arthur Ribeiro](www.linkedin.com/in/arthurrsdn)
* **Email:** [arthur.ribeiro.sn@gmail.com](mailto:arthur.ribeiro.sn@gmail.com)

---
