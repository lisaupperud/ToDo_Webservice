package com.lisaanna.ws_todo.repository;

import com.lisaanna.ws_todo.entity.DeletedTask;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DeletedTaskRepository extends MongoRepository<DeletedTask, String> {
    @Query("{'tags': ?0}")
    List<DeletedTask> findByTags(String tags);
}
