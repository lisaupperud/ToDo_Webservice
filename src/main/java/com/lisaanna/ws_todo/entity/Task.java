package com.lisaanna.ws_todo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.mongodb.core.mapping.*;
import java.util.List;

@Document(collection = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Field("Name")
    @NotBlank
    private String name;

    @Field("Description")
    private String description;

    @Field("Is completed")
    @NotNull
    private Boolean completed = false;

    @Field("Tags")
    private List<String> tags;

    public Task() {}

    public Task(String id, String name, String description, Boolean completed, List<String> tags) {
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
