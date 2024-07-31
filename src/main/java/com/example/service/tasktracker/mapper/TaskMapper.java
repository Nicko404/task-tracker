package com.example.service.tasktracker.mapper;

import com.example.service.tasktracker.entity.Task;
import com.example.service.tasktracker.mapper.delegate.TaskMapperDelegate;
import com.example.service.tasktracker.web.model.TaskResponse;
import com.example.service.tasktracker.web.model.UpsertTaskRequest;
import org.mapstruct.*;

@DecoratedWith(TaskMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    Task requestToTask(UpsertTaskRequest request);

    Task requestToTask(String id, UpsertTaskRequest request);

    @Mapping(source = "task.id", target = "id")
    TaskResponse taskToTaskResponse(Task task);
}
