package com.example.service.tasktracker.web.model;

import lombok.Data;

@Data
public class UpsertUserRequest {

    private String username;

    private String email;
}
