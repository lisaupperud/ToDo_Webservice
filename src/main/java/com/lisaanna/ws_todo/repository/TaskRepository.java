package com.lisaanna.ws_todo.repository;

import com.lisaanna.ws_todo.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {}
