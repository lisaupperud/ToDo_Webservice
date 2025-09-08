package com.lisaanna.ws_todo.controller;

import com.lisaanna.ws_todo.entity.Task;
import com.lisaanna.ws_todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // get all: suggestion "/"?
    @GetMapping("/")
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    // get filtered
    //@GetMapping

    // create new
    @PostMapping("/new")
    public Task save(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    // update
    //@PutMapping

    //delete specific
    //@DeleteMapping

    // delete all completed
    //@DeleteMapping

}
