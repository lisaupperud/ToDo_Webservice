package com.lisaanna.ws_todo.controller;

import com.lisaanna.ws_todo.repository.TaskRepository;
import com.lisaanna.ws_todo.service.TaskDTO;
import com.lisaanna.ws_todo.service.TaskService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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
    @RateLimiter(name = "myRateLimiter")
    public ResponseEntity<List<TaskDTO>> findNotCompleted() {
        List<TaskDTO> uncompletedTasks = taskService.findNotCompleted();
        if (uncompletedTasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(uncompletedTasks);
    }

    @GetMapping("/all")
    @RateLimiter(name = "myRateLimiter")
    public ResponseEntity<List<TaskDTO>> findAll() {
        List<TaskDTO> tasks = taskService.findAllTasks();
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/{name}")
    @RateLimiter(name = "myRateLimiter")
    public ResponseEntity<TaskDTO> findTaskByName(@PathVariable String name) {
        Optional<TaskDTO> foundTask = taskService.findTaskByName(name);

        if (foundTask.isPresent()) {
            TaskDTO taskDTO = foundTask.get();
            return ResponseEntity.ok().body(taskDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search/name/{name}")
    @RateLimiter(name = "myRateLimiter")
    public ResponseEntity<List<TaskDTO>> searchTasksByName(@PathVariable String name) {
        List<TaskDTO> matchedTasks = taskService.findTasksByNamePartial(name);

        if (matchedTasks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(matchedTasks);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TaskDTO> findTaskById(@PathVariable String id) {
        Optional<TaskDTO> foundTask = taskService.findTaskById(id);

        if (foundTask.isPresent()) {
            TaskDTO taskDTO = foundTask.get();
            return ResponseEntity.ok().body(taskDTO);
        }
        return ResponseEntity.notFound().build();
    }

    // TODO: look @ path's --> might change to /sort/{tags}
    @GetMapping("/tag/{tags}")
    @RateLimiter(name = "myRateLimiter")
    public ResponseEntity<List<TaskDTO>> findByTags(@PathVariable String tags) {
        List<TaskDTO> taskByTags = taskService.findTaskByTag(tags);

        if (taskByTags.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(taskByTags);
    }

    @GetMapping("/sort/priority")
    @RateLimiter(name = "myRateLimiter")
    public ResponseEntity<List<TaskDTO>> findAndSortByPriority() {
        List<TaskDTO> sortedTasks = taskService.findTasksWithPriority();
        if (sortedTasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok().body(sortedTasks);
    }

    @GetMapping("/sort/no-priority")
    @RateLimiter(name = "myRateLimiter")
    public ResponseEntity<List<TaskDTO>> findByNoPriority() {
        List<TaskDTO> noPriorityTasks = taskService.findTaskWithoutPriority();
        if (noPriorityTasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok().body(noPriorityTasks);
    }

    // create new
    @PostMapping("/new")
    @RateLimiter(name = "myRateLimiter")
    public ResponseEntity<TaskDTO> save(@RequestBody TaskDTO taskDTO) {

        if (taskDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        TaskDTO newTask = taskService.saveNewTask(taskDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    // update individual fields of choice
    @PatchMapping("/update/{id}")
    @RateLimiter(name = "myRateLimiter")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable String id, @RequestBody TaskDTO taskDTO) {
        TaskDTO updatedTaskDTO = taskService.updateTask(id, taskDTO);

        if (updatedTaskDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(updatedTaskDTO);
    }

    // update 'complete' field to true
    @PatchMapping("/complete/{id}")
    @RateLimiter(name = "myRateLimiter")
    public ResponseEntity<TaskDTO> completeTask(@PathVariable String id) {
        TaskDTO updatedTaskDTO = taskService.completeTask(id);
        if (updatedTaskDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedTaskDTO);
    }

    // move task to trashcan
    @PutMapping("/trash/{id}")
    @RateLimiter(name = "myRateLimiter")
    public ResponseEntity<String> moveTaskToTrash(@PathVariable String id) {
        boolean success = taskService.moveToTrash(id);
        if (success) {
            return ResponseEntity.ok("Task moved to trash");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/trash/completed")
    @RateLimiter(name = "myRateLimiter")
    public ResponseEntity<String> moveCompletedTasksToTrash() {
        boolean success = taskService.moveAllCompletedToTrash();
        if (!success) {
            return ResponseEntity.ok("No completed tasks found. Get to work!");
        }
        return ResponseEntity.ok("All completed tasks moved to trash");
    }

    // restore task from trashcan
    @PutMapping("/restore/{id}")
    @RateLimiter(name = "myRateLimiter")
    public ResponseEntity<TaskDTO> restoreTaskFromTrash(@PathVariable String id) {
        boolean success = taskService.restoreFromTrash(id);
        if (!success) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Optional<TaskDTO> foundTask = taskService.findTaskById(id);

        return foundTask
                .map(taskDTO -> ResponseEntity.ok().body(taskDTO))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/tag/stats")
    @RateLimiter(name = "myRateLimiter")
    public ResponseEntity<List<TaskRepository.TagCount>> getTaskStats() {
        if (taskService.getMostUsedTags().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(taskService.getMostUsedTags());
    }
}
