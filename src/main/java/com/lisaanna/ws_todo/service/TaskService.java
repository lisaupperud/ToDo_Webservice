package com.lisaanna.ws_todo.service;

import com.lisaanna.ws_todo.component.TaskMapper;
import com.lisaanna.ws_todo.entity.Task;
import com.lisaanna.ws_todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return taskRepository.findByNotCompleted().stream().map(taskMapper::mapToTaskDTO).collect(Collectors.toList());
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

    // get - by tags
    public List<TaskDTO> findTaskByTag(String tag) {
        return taskRepository.findByTags(tag).stream().map(taskMapper::mapToTaskDTO).collect(Collectors.toList());
    }

    // post
    public TaskDTO saveNewTask(TaskDTO taskDTO) {
        Task task = taskMapper.mapToTask(taskDTO);
        taskRepository.save(task);
        return taskDTO;
    }

    // patch
    public TaskDTO updateTask(String id, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Task not found"));

        if (taskDTO.getName() != null) {
            existingTask.setName(taskDTO.getName());
        }
        if (taskDTO.getDescription() != null) {
            existingTask.setDescription(taskDTO.getDescription());
        }
        if (taskDTO.getCompleted()) {
            existingTask.setCompleted(taskDTO.getCompleted());
        }
        if (taskDTO.getTags() != null) {
            existingTask.setTags(taskDTO.getTags());
        }

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.mapToTaskDTO(updatedTask);
    }

    // patch
    public TaskDTO completeTask(String id) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Task not found"));

        if (!existingTask.getCompleted()) {
            existingTask.setCompleted(true);
        }

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.mapToTaskDTO(updatedTask);
    }

    /*
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
    */

    // delete
    public void moveToTrash(String id) {


    }
}
