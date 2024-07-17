# User Management Service

This is a User Management Service built with Spring Boot and PostgreSQL. The service provides CRUD operations for user data, including the ability to search users by first name, last name, or both. Additionally, it supports importing user data from a CSV file.

## Prerequisites

- Docker and Docker Compose installed on your machine
- Java Development Kit (JDK) 11 or later
- Gradle

## Getting Started

You can start the service in two ways:
1. Using Gradle to build the project and Docker Compose to start the services.
2. Using a dedicated Bash script to automate the build and start process 
	./script.sh

### Option 1: Using Gradle and Docker Compose

**Build the project using Gradle:**
	
	./gradlew clean build

**Start the services using Docker Compose:**
	
	docker-compose up --build

This will build the Docker image for the User Management Service and start both the PostgreSQL database and the application.

**Access the service:**

The application will be accessible at http://localhost:8080 and The API documentation is available through Swagger. You can access it at the following URL:
	
	http://localhost:8080/swagger-ui/index.html

### Option 2: Using the Bash Script

**Make the Bash script executable:**

	chmod +x script.sh

**Run the Bash script:**

	./script.sh

This script will clean and build the project using Gradle, then start the services using Docker Compose.

**Access the service:**

The application will be accessible at http://localhost:8080 and The API documentation is available through Swagger. You can access it at the following URL:
	
	http://localhost:8080/swagger-ui/index.html