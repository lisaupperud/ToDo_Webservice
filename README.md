# To-Do List REST API

A RESTful web service for managing a To-Do list, built with Java, Spring Boot, and MongoDB.  
Users can create, update, retrieve, and delete tasks via standard HTTP methods.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Build](#build)
  - [Run](#run)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)
- [License](#license)

## Features

- Add new tasks
- Edit existing tasks
- Delete tasks
- Retrieve all tasks or a single task
- Input validation for task data

## Technologies

- Java 17+
- Spring Boot
- Gradle
- MongoDB
- Spring Data MongoDB
- Spring Validation

## Getting Started

### Prerequisites

- Java 17 or later installed
- Gradle installed (or use the Gradle wrapper included)
- MongoDB installed and running locally or accessible via connection string

### Build

You can build the project using Gradle. If you're using the Gradle wrapper (recommended), run:

```bash
./gradlew clean build
