package com.lisaanna.ws_todo.validation;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvValidator {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String dbName;

    @PostConstruct
    public void validate() {
        if (mongoUri == null || mongoUri.isBlank()) {
            throw new IllegalStateException("Missing MongoDB connection string");
        }
        if (dbName == null || dbName.isBlank()) {
            throw new IllegalStateException("Missing MongoDB database name");
        }
        System.out.println("Connected to " + dbName);
    }
}
