package com.lisaanna.ws_todo.controller;

import com.lisaanna.ws_todo.service.TaskDTO;
import com.lisaanna.ws_todo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public ResponseEntity<List<TaskDTO>> findNotCompleted() {
        List<TaskDTO> uncompletedTasks = taskService.findNotCompleted();
        if (uncompletedTasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(uncompletedTasks);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskDTO>> findAll() {
        List<TaskDTO> tasks = taskService.findAllTasks();
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/{name}")
    public ResponseEntity<TaskDTO> findTaskByName(@PathVariable String name) {
        Optional<TaskDTO> foundTask = taskService.findTaskByName(name);

        if (foundTask.isPresent()) {
            TaskDTO taskDTO = foundTask.get();
            return ResponseEntity.ok().body(taskDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/tag/{tags}")
    public ResponseEntity<List<TaskDTO>> findByTags(@PathVariable String tags) {
        List<TaskDTO> taskByTags = taskService.findTaskByTag(tags);

        if (taskByTags.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(taskByTags);
    }

    // create new
    @PostMapping("/new")
    public ResponseEntity<TaskDTO> save(@RequestBody TaskDTO taskDTO) {

        if (taskDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        TaskDTO newTask = taskService.saveNewTask(taskDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    // update individual fields of choice
    @PatchMapping("/update/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable String id, @RequestBody TaskDTO taskDTO) {
        if (taskDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        TaskDTO updatedTaskDTO = taskService.updateTask(id, taskDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTaskDTO);
    }

    // update 'complete' field to true
    @PatchMapping("/complete/{id}")
    public ResponseEntity<TaskDTO> completeTask(@PathVariable String id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        TaskDTO updatedTaskDTO = taskService.completeTask(id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTaskDTO);
    }

    // move task to trashcan
    @PutMapping("/trash/{id}")
    public ResponseEntity<String> moveTaskToTrash(@PathVariable String id) {
        boolean success = taskService.moveToTrash(id);

        if (success) {
            return ResponseEntity.ok("Task moved to trash");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // restore task from trashcan
    @PutMapping("/restore/{id}")
    public ResponseEntity<TaskDTO> restoreTaskFromTrash(@PathVariable String id) {
        boolean success = taskService.restoreFromTrash(id);
        if (!success) {
            return ResponseEntity.notFound().build();
        }

        Optional<TaskDTO> foundTask = taskService.findTaskById(id);
        return foundTask
                .map(taskDTO -> ResponseEntity.ok().body(taskDTO))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

}
