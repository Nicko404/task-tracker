package com.example.service.tasktracker.web.model;

import lombok.Data;

@Data
public class UpsertTaskRequest {

    private String name;

    private String description;

    private String status;

    private String authorId;

    private String assigneeId;
}