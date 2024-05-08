# CRESWAVE_CODE_TEST Application

## Overview
CRESWAVE_CODE_TEST is a Spring Boot application designed for a coding test scenario. It involves the management of a simple blogging platform where users can create blogs and comment on them. The application leverages Spring Data JPA for database operations and provides RESTful APIs for creating, editing, and deleting blogs and comments.

## Setup
To set up the CRESWAVE_CODE_TEST application, ensure you have the following prerequisites:
1. Java Development Kit (JDK) installed on your machine.
2. MySQL database server installed and running locally.
3. Text editor or IDE for viewing and editing the application code.

## Configuration
The application uses configuration properties as specified in app.properties file. Below are key considerations:
- **spring.datasource.username**: Username for accessing the MySQL database. Set it to whatever username you use to access your MySQL Server
- **spring.datasource.password**: Password for the specified database user. Set it to whatever password you use to access your MySQL Server

```properties   
    spring.datasource.url=jdbc:mysql://localhost:3306/blog_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password
```


## Usage
1. Clone or download the CRESWAVE_CODE_TEST application from the repository.
2. Open the application in your preferred IDE or text editor (I suggest InteliJ).
3. Configure the database connection properties in the `application.properties` file.
4. Run the application using Maven.
5. Access the RESTful APIs provided by the application to create, edit, and delete blogs and comments. It's recommended to use Postman for ease of use.


## JWT Authentication
Considerations for JWT authentication:
- This project JWT (JSON Web Tokens) for securing the RESTful APIs.
- Add JWT tokens to requests for authorized access to protected endpoints.
- Users can use Postman for testing the authenticated endpoints.

## Swagger
Users can also use Swagger for testing the endpoints if preferred.
Access the docs on: http://localhost:8080/swagger-ui/index.html#

## Contributing
Contributions to the CRESWAVE_CODE_TEST application are welcome. If you encounter any issues or have suggestions for improvements, please feel free to open an issue or submit a pull request on the project repository.

## License
The CRESWAVE_CODE_TEST application is open-source software licensed under the MIT License. You are free to modify and distribute the application as per the terms of the license.
