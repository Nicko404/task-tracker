package com.example.service.tasktracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "tasks")
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    private String id;

    private String name;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private TaskStatus status;

    private String authorId;

    @ReadOnlyProperty
    private User author;

    private String assigneeId;

    @ReadOnlyProperty
    private User assignee;

    private Set<String> observersIds = new HashSet<>();

    @ReadOnlyProperty
    private Set<User> observers = new HashSet<>();

    public void addObserverId(String observerId) {
        observersIds.add(observerId);
    }

    public void addObserver(User observer) {
        observers.add(observer);
    }
}
