package com.lisaanna.ws_todo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeletedTaskRepository extends MongoRepository<DeletedTask, String> {
}
