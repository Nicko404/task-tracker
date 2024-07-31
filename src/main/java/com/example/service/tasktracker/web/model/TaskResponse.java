package com.example.service.tasktracker.web.model;

import com.example.service.tasktracker.entity.User;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class TaskResponse {

    private String id;

    private String name;

    private String description;

    private String status;

    private User author;

    private User assignee;

    private Set<User> observers = new HashSet<>();
}
