package com.lisaanna.ws_todo.service;

import com.lisaanna.ws_todo.entity.DeletedTask;
import com.lisaanna.ws_todo.entity.Task;
import com.lisaanna.ws_todo.repository.DeletedTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeletedTaskService {

    @Autowired
    DeletedTaskRepository deletedTaskRepository;



    public DeletedTaskService(DeletedTaskRepository deletedTaskRepository) {
        this.deletedTaskRepository = deletedTaskRepository;
    }

    // GET - all

    // GET - filtered

    // DELETE - by id
    public void deleteTaskFromTrash(String id) {

        DeletedTask taskToDelete = deletedTaskRepository.findById(id).orElse(null);

        if (taskToDelete != null) {
            deletedTaskRepository.delete(taskToDelete);
        } else {
            throw new NullPointerException();
        }

    }

    // DELETE - all
}
