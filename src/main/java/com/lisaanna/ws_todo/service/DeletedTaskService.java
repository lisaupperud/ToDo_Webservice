package com.lisaanna.ws_todo.service;

import com.lisaanna.ws_todo.component.DeletedTaskMapper;
import com.lisaanna.ws_todo.model.DeletedTask;
import com.lisaanna.ws_todo.repository.DeletedTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeletedTaskService {

    @Autowired
    DeletedTaskRepository deletedTaskRepository;

    DeletedTaskMapper deletedTaskMapper;

    public DeletedTaskService(DeletedTaskRepository deletedTaskRepository,  DeletedTaskMapper deletedTaskMapper) {
        this.deletedTaskRepository = deletedTaskRepository;
        this.deletedTaskMapper = deletedTaskMapper;
    }

    // GET - all
    public List<DeletedTaskDTO> findAllDeletedTasks(){
        return deletedTaskRepository.findAll().stream().map(deletedTaskMapper::mapToDeletedTaskDTO).collect(Collectors.toList());
    }

    // GET - filtered
    public List<DeletedTaskDTO> findDeletedTaskByTag(String tag){
        return deletedTaskRepository.findByTags(tag).stream().map(deletedTaskMapper::mapToDeletedTaskDTO).collect(Collectors.toList());
    }

    // DELETE - by id
    public boolean deleteTaskFromTrash(String id) {
        DeletedTask taskToDelete = deletedTaskRepository.findById(id).orElse(null);

        if (taskToDelete != null) {
            deletedTaskRepository.delete(taskToDelete);
            return true;
        } else {
            return false;
        }
    }

    // DELETE - all
    public boolean deleteAllTasks() {
        if (deletedTaskRepository.findAll().isEmpty()) {
            return false;
        } else {
            deletedTaskRepository.deleteAll();
            return true;
        }
    }
}
