# Student Management System

## Overview
``This application is designed to ease the process of managing students within an educational institution. It allows administrators to admit students, manage courses, and perform various operations, while students can update their profiles and manage their course enrollments.``

### Features Implemented
#### Logging
`` Implemented logging across the application to monitor activity and track errors.Configured logging levels specifically for the student management services to capture relevant information without overwhelming the logs.``

##### Exception Handling
``Centralized exception handling to manage application errors gracefully. Provided meaningful error messages and HTTP status codes for different error scenarios.``

##### Swagger Integration
``Integrated Swagger for API documentation, enabling easy exploration and testing of API endpoints.Swagger UI provides an interactive interface for users to understand available operations and parameters.``

##### Database Configuration
``Configured MySQL as the database for storing student and course information. Used JPA with Hibernate for object-relational mapping and database interactions.``

### Initial Setup

``Step 1: Clone the Repository``
git clone https://github.com/sandeep/student-management-system.git
cd student-management-system

``Step 2: Configure MySQL``
Create a database named platform.
Update the application.properties file with your MySQL credentials:
spring.datasource.url=jdbc:mysql://localhost:3306/platform
spring.datasource.username=root
spring.datasource.password=your_password

##### 
``` Note : Set Hibernate ddl to "create" during initial run and change to "update" post the tables gets created. this will ease the process of creating Tables. Since Auto reload is supported, Further changes can be captured.```

spring.jpa.hibernate.ddl-auto=create

``Step 3: Build the Project``
mvn clean install

``Step 4: Run the Application``
mvn spring-boot:run

``Step 5: Access Swagger UI``
Open your browser and navigate to http://localhost:8080/swagger-ui.html to access the API documentation.

``Step 6: Testing with Postman``
Use Postman to test the API endpoints. Admin can log in using predefined credentials, and students can validate themselves using their student code and date of birth.

## Future Enhancements
Spring Security : not implemented
Unit Testing :  Implemented unit tests for key components to ensure code reliability and correctness.
Docker integration : To run in local docker container
Static code analysis : To check the code quality and linting issues
CICD workflows : Can enhance it to make Deployment ready using CICD workflows