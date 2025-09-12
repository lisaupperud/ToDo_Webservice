package com.lisaanna.ws_todo.service;

import com.lisaanna.ws_todo.model.Priority;
import jakarta.validation.constraints.*;
import java.util.List;

public class TaskDTO {

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @Size(min = 1, max = 500)
    private String description;

    @NotNull
    private boolean completed;

    private List<String> tags;

    private Priority priority;

    public TaskDTO() {}

    public TaskDTO(String name, String description, boolean completed, List<String> tags,  Priority priority) {
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.tags = tags;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
