package com.lisaanna.ws_todo.repository;

import com.lisaanna.ws_todo.entity.DeletedTask;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeletedTaskRepository extends MongoRepository<DeletedTask, String> {
}
