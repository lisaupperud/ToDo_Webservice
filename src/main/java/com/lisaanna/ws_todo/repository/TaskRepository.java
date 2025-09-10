package com.lisaanna.ws_todo.repository;

import com.lisaanna.ws_todo.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task, String> {

    Optional<Task> findByName(String name);

    @Query("{'completed': false}")
    List<Task> findByNotCompleted();

    @Query("{'tags': ?0}")
    List<Task> findByTags(String tags);

    String id(String id);
}
