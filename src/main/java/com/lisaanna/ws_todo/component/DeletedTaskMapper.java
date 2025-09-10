package com.lisaanna.ws_todo.component;

import com.lisaanna.ws_todo.entity.DeletedTask;
import com.lisaanna.ws_todo.service.DeletedTaskDTO;
import org.springframework.stereotype.Component;

@Component
public class DeletedTaskMapper {

    public DeletedTask mapToDeletedTask(DeletedTaskDTO deletedTaskDTO) {
        DeletedTask deletedTask = new DeletedTask();
        deletedTask.setName(deletedTaskDTO.getName());
        deletedTask.setDescription(deletedTaskDTO.getDescription());
        deletedTask.setCompleted(deletedTaskDTO.getCompleted());
        deletedTask.setTags(deletedTaskDTO.getTags());
        return deletedTask;
    }

    public DeletedTaskDTO mapToTaskDTO(DeletedTask deletedTask) {
        DeletedTaskDTO deletedTaskDTO = new DeletedTaskDTO();
        deletedTaskDTO.setName(deletedTask.getName());
        deletedTaskDTO.setDescription(deletedTask.getDescription());
        deletedTaskDTO.setCompleted(deletedTask.getCompleted());
        deletedTaskDTO.setTags(deletedTask.getTags());
        return deletedTaskDTO;
    }
}
