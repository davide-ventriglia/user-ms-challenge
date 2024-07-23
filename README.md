# User Management Service

This is a User Management Service built with Spring Boot and PostgreSQL. The service provides CRUD operations for user data, including the ability to search users by first name, last name, or both. Additionally, it supports importing user data from a CSV file.

## Prerequisites

- Docker and Docker Compose installed on your machine
- Java Development Kit (JDK) 17 or later
- Gradle

## Build & Deploy

You can start the service in two ways:
1. Using Gradle to build the project and Docker Compose to start the services.
2. Using a dedicated Bash script to automate the build and start process 
	./script.sh

### Option 1: Using Gradle and Docker Compose

To build this project run from the project folder

```bash
  ./gradlew build
```

or if you want to skip test

```bash
  ./gradlew build -x test
```

**Start the services using Docker Compose:**
	
To deploy this project you should have docker and docker-compose installed on your machine and run from the project folder

```bash
   docker-compose up --build
```

This will build the Docker image for the User Management Service and start both the PostgreSQL database and the application.

### Option 2: Using the Bash Script

**Make the Bash script executable:**

	chmod +x script.sh

**Run the Bash script:**

	./script.sh

This script will clean and build the project using Gradle, then start the services using Docker Compose.

## Test the service

The application will be accessible at http://localhost:8080 and The API documentation is available through Swagger. You can access it at the following URL:
	
	http://localhost:8080/swagger-ui/index.html
	
You can test all the APIs to create, search, update and delete users.
You can test the API to bulk import the users from a csv file (consider using the already provided file *import-file.csv* in the project folder).
	
## Troubleshooting

- If you encounter problems during the build phase because of the tests, to avoid build errors cosider using 

```bash
  ./gradlew build -x test
```

- If you encounter problems during deployments phase (maybe due to more than one deployment attempts), consider cleaning all the resources first using 

```bash
  docker-compose down -v --rmi local
```

- For any other problem, consider to open an issue on github for further investigation.