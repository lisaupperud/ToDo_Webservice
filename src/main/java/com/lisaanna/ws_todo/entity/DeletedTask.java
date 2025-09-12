package com.lisaanna.ws_todo.entity;

import com.lisaanna.ws_todo.Priority;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "trashcan")
public class DeletedTask {
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
    @Field("priority")
    private Priority priority;

    public DeletedTask() {}

    public DeletedTask(String id, String name, String description, boolean completed, List<String> tags,  Priority priority) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.tags = tags;
        this.priority = priority;
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
