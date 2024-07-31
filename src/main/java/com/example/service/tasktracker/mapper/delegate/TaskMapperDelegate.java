package com.example.service.tasktracker.mapper.delegate;

import com.example.service.tasktracker.entity.Task;
import com.example.service.tasktracker.entity.TaskStatus;
import com.example.service.tasktracker.mapper.TaskMapper;
import com.example.service.tasktracker.web.model.UpsertTaskRequest;

public abstract class TaskMapperDelegate implements TaskMapper {

    @Override
    public Task requestToTask(UpsertTaskRequest request) {
        Task task = new Task();
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        task.setAuthorId(request.getAuthorId());
        task.setAssigneeId(request.getAssigneeId());

        switch (request.getStatus()) {
            case "TODO" -> task.setStatus(TaskStatus.TODO);
            case "IN_PROGRESS" -> task.setStatus(TaskStatus.IN_PROGRESS);
            case "DONE" -> task.setStatus(TaskStatus.DONE);
            default -> task.setStatus(TaskStatus.TODO);
        }
        return task;
    }

    @Override
    public Task requestToTask(String id, UpsertTaskRequest request) {
        Task task = requestToTask(request);
        task.setId(id);
        return task;
    }
}