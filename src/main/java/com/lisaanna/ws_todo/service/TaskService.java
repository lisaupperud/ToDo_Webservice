package com.lisaanna.ws_todo.service;

import com.lisaanna.ws_todo.component.TaskMapper;
import com.lisaanna.ws_todo.model.Priority;
import com.lisaanna.ws_todo.model.Task;
import com.lisaanna.ws_todo.repository.TaskRepository;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final MongoCollection<Document> taskCollection;
    private final MongoCollection<Document> trashCollection;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, MongoClient mongoClient, @Value("${spring.data.mongodb.database}") String dbName) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.taskCollection = mongoClient.getDatabase(dbName).getCollection("task");
        this.trashCollection = mongoClient.getDatabase(dbName).getCollection("trashcan");
    }

    // get - auto filtered
    public List<TaskDTO> findNotCompleted() {
        return taskRepository.findByNotCompleted().stream().map(taskMapper::mapToTaskDTO).collect(Collectors.toList());
    }

    // get - all
    public List<TaskDTO> findAllTasks() {
        return taskRepository.findAll().stream().map(taskMapper::mapToTaskDTO).collect(Collectors.toList());
    }

    // get - single by name
    public Optional<TaskDTO> findTaskByName(String name) {
        Optional<Task> foundTask = taskRepository.findByName(name);
        return foundTask.map(taskMapper::mapToTaskDTO);
    }

    // get - single by id
    public Optional<TaskDTO> findTaskById(String id) {
        Optional<Task> foundTask = taskRepository.findById(id);
        return foundTask.map(taskMapper::mapToTaskDTO);
    }

    // get - by tags
    public List<TaskDTO> findTaskByTag(String tag) {
        return taskRepository.findByTags(tag).stream().map(taskMapper::mapToTaskDTO).collect(Collectors.toList());
    }

    // get - & sort by priority
    public List<TaskDTO> findTasksWithPriority() {
        return taskRepository.findTasksWithPriority()
                .stream()
                .map(taskMapper::mapToTaskDTO)
                .sorted(Comparator.comparing(TaskDTO::getPriority).reversed())
                .toList();
    }

    // get - without prio
    public List<TaskDTO> findTaskWithoutPriority() {
        return taskRepository.findTasksWithoutPriority().stream().map(taskMapper::mapToTaskDTO).toList();
    }

    // post
    public TaskDTO saveNewTask(TaskDTO taskDTO) {
        Task task = taskMapper.mapToTask(taskDTO);
        taskRepository.save(task);
        return taskDTO;
    }

    // patch
    public TaskDTO updateTask(String id, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElse(null);

        if (taskDTO.getName() != null) {
            existingTask.setName(taskDTO.getName());
        }
        if (taskDTO.getDescription() != null) {
            existingTask.setDescription(taskDTO.getDescription());
        }
        if (taskDTO.getCompleted()) {
            existingTask.setCompleted(taskDTO.getCompleted());
        }
        if (taskDTO.getTags() != null) {
            existingTask.setTags(taskDTO.getTags());
        }

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.mapToTaskDTO(updatedTask);
    }

    // patch
    public TaskDTO completeTask(String id) {
        Task existingTask = taskRepository.findById(id)
                .orElse(null);

        if (!existingTask.getCompleted()) {
            existingTask.setCompleted(true);
        }

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.mapToTaskDTO(updatedTask);
    }

    // Move document from task to trashcan
    public boolean moveToTrash(String id) {
        return moveDocument(taskCollection, trashCollection, id);
    }

    // Move all completed tasks to trashcan
    public boolean moveAllCompletedToTrash() {
        List<Task> completedTasks = taskRepository.findByCompleted();
        if (!completedTasks.isEmpty()) {
            for (Task completedTask : completedTasks) {
                moveDocument(taskCollection, trashCollection, completedTask.getId());
            }
            return true;
        }
        return false;
    }

    // Move document from trashcan to task
    public boolean restoreFromTrash(String id) {
        return moveDocument(trashCollection, taskCollection, id);
    }

    private boolean moveDocument(MongoCollection<Document> from,
                                 MongoCollection<Document> to,
                                 String id) {
        Bson filter;
        try {
            filter = Filters.eq("_id", new ObjectId(id));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid ObjectId format: " + id);
            return false;
        }

        Document doc = from.find(filter).first();
        if (doc == null) {
            System.out.println("Document not found in source collection.");
            return false;
        }

        to.insertOne(doc);
        from.deleteOne(filter);
        return true;
    }

    public List<TaskRepository.TagCount> getMostUsedTags(){
        List<TaskRepository.TagCount> tags = taskRepository.findMostUsedTags(10);
        return tags;
    }

}
