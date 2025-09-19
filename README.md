# To-Do List REST API

A RESTful web service for managing a To-Do list, built with Java, Spring Boot, and MongoDB.  
Users can create, update, retrieve, and delete tasks via standard HTTP methods.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Build](#build)
- [API Endpoints](#api-endpoints)
- [Further Development](#further-development)

## Features

- Retrieve all or a single task
- Add new tasks
- Edit existing tasks
- Delete all or single tasks
- Sort and filter tasks
- Input validation for task data

## Technologies

- Java 17+
- Spring Boot
- Gradle
- MongoDB
- Spring Web
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
```

## API Endpoints

### TaskController (`/v1/tasks`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/v1/tasks/` | Get all **not completed** tasks |
| GET | `/v1/tasks/all` | Get **all** tasks |
| GET | `/v1/tasks/{name}` | Get a single task by **name** |
| GET | `/v1/tasks/search/name/{name}` | Get all tasks containing `{name}` in their name |
| GET | `/v1/tasks/id/{id}` | Get a single task by **ID** |
| GET | `/v1/tasks/tag/{tags}` | Get tasks filtered by **tag(s)** |
| GET | `/v1/tasks/sort/priority` | Get all tasks with priority, **sorted by priority** |
| GET | `/v1/tasks/sort/no-priority` | Get all tasks **without priority** |
| GET | `/v1/tasks/tag/stats` | Get statistics for the `tags` field |
| POST | `/v1/tasks/new` | Create a new task |
| PATCH | `/v1/tasks/update/{id}` | Update individual fields in a single task |
| PATCH | `/v1/tasks/complete/{id}` | Mark a task as **completed** |
| PUT | `/v1/tasks/trash/{id}` | Move a task to the **trashcan** |
| PUT | `/v1/tasks/trash/completed` | Move all completed tasks to the **trashcan** |
| PUT | `/v1/tasks/restore/{id}` | Restore a task from the **trashcan** |

---

### DeletedTaskController (`/v1/trashcan`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/v1/trashcan/` | Get all deleted tasks |
| GET | `/v1/trashcan/tag/{tags}` | Filter deleted tasks by **tag(s)** |
| DELETE | `/v1/trashcan/delete/{id}` | Delete a single task permanently |
| DELETE | `/v1/trashcan/delete/all` | Delete all tasks permanently |

## Further Development

- Add **drag & drop** functionality to the UI  
- Add **deadline** as a task attribute  
- Add **started** as a task attribute
- Separate **frontend** and **backend** into independent projects  
- Refine and clean up the **UI**  
- Launch the **application**







