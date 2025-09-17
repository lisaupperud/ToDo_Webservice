package com.lisaanna.ws_todo.component;

import com.lisaanna.ws_todo.model.Task;
import com.lisaanna.ws_todo.service.TaskDTO;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task mapToTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.getCompleted());
        task.setTags(taskDTO.getTags());
        task.setPriority(taskDTO.getPriority());
        return task;
    }

    public TaskDTO mapToTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setName(task.getName());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setCompleted(task.getCompleted());
        taskDTO.setTags(task.getTags());
        taskDTO.setPriority(task.getPriority());
        return taskDTO;
    }
}
