package com.lisaanna.ws_todo.service;

import com.lisaanna.ws_todo.entity.Task;
import com.lisaanna.ws_todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // get - all
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    // get - single
    public Optional<Task> findTaskById(String id) {
        return taskRepository.findById(id);
    }

    // get - auto filtered
    public List<Task> findNotCompleted() {
        List<Task> notCompleted = taskRepository.findAll();

        for (Task task : notCompleted) {
            if (task.getCompleted() == false) {
                notCompleted.add(task);
            }
        }

        return notCompleted;
    }

    // get - filtered by input


    // post
    public Task save(Task task) {
        return taskRepository.save(task);
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
