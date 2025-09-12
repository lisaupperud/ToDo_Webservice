package com.lisaanna.ws_todo.repository;

import com.lisaanna.ws_todo.model.Priority;
import com.lisaanna.ws_todo.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task, String> {

    Optional<Task> findByName(String name);

    @Query("{'completed': false}")
    List<Task> findByNotCompleted();

    @Query("{'completed': true}")
    List<Task> findByCompleted();

    @Query("{'tags': ?0}")
    List<Task> findByTags(String tags);

    @Query("{ 'priority': { '$exists': true } }")
    List<Task> findTasksWithPriority();

    @Query("{ 'priority': { '$exists': false } }")
    List<Task> findTasksWithoutPriority();


}
