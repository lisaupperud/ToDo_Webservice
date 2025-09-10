package com.lisaanna.ws_todo.entity;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.*;
import java.util.List;

@Document(collection = "task")
public class Task {
    @Id
    private String id;
    @Field("name")
    private String name;
    @Field("description")
    private String description;
    @Field("is completed")
    private boolean completed;
    @Field("tags")
    private List<String> tags;

    public Task() {}

    public Task(String id, String name, String description, boolean completed, List<String> tags) {
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
