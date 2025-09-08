package com.lisaanna.ws_todo.service;

import com.lisaanna.ws_todo.component.TaskMapper;
import com.lisaanna.ws_todo.entity.Task;
import com.lisaanna.ws_todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    // get - auto filtered
    public List<TaskDTO> findNotCompleted() {
        List<Task> notCompleted = new ArrayList<>();

        for (Task task : taskRepository.findAll()) {
            if (!task.getCompleted()) {
                notCompleted.add(task);
            }
        }

        return notCompleted.stream().map(taskMapper::mapToTaskDTO).collect(Collectors.toList());
    }

    // get - all
    public List<TaskDTO> findAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(taskMapper::mapToTaskDTO).collect(Collectors.toList());
    }

    // get - single
    public Optional<TaskDTO> findTaskByName(String name) {
        Optional<Task> foundTask = taskRepository.findByName(name);
        return foundTask.map(taskMapper::mapToTaskDTO);
    }


    // get - filtered by input
    // TODO: Use queries in repo for specified filters

    // post
    public TaskDTO saveNewTask(TaskDTO taskDTO) {
        Task task = taskMapper.mapToTask(taskDTO);
        taskRepository.save(task);
        return taskDTO;
    }

    // put
    public Task update(String id, TaskDTO task) {

        Task updatedTask = taskRepository.findById(id).orElse(null);

        if (updatedTask != null) {
            updatedTask.setName(task.getName());
            updatedTask.setDescription(task.getDescription());
            updatedTask.setCompleted(task.getCompleted());
            updatedTask.setTags(task.getTags());
        } else {
            throw new NullPointerException();
        }

        return taskRepository.save(updatedTask);
    }

    // delete
    public void delete(String id) {

        Task taskToDelete = taskRepository.findById(id).orElse(null);

        if (taskToDelete != null) {
            taskRepository.delete(taskToDelete);
        } else {
            throw new NullPointerException();
        }

    }
}
