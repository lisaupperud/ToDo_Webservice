package com.lisaanna.ws_todo.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class DeletedTaskDTO {

    private String name;

    private String description;

    private boolean completed;

    private List<String> tags;

    public DeletedTaskDTO() {}

    public DeletedTaskDTO(String name, String description, boolean completed, List<String> tags) {
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.tags = tags;
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
}
