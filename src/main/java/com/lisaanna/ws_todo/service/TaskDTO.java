package com.lisaanna.ws_todo.service;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

public class TaskDTO {

    @Id
    private String id;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Boolean completed = false;

    private List<String> tags;

    public TaskDTO() {}

    public TaskDTO(String id, String name, String description, Boolean completed, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
