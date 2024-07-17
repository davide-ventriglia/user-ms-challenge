#!/bin/bash

echo "Stopping and removing existing containers..."
docker-compose down

echo "Building the project with Gradle..."
./gradlew clean build -x test

echo "Building and starting Docker containers..."
docker-compose up --build
