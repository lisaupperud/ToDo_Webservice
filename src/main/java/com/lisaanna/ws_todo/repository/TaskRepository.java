package com.lisaanna.ws_todo.repository;

import com.lisaanna.ws_todo.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task, String> {
    Optional<Task> findByName(String name);
}
