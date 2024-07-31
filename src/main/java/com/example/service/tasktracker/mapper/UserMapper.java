package com.example.service.tasktracker.mapper;

import com.example.service.tasktracker.entity.User;
import com.example.service.tasktracker.web.model.UpsertUserRequest;
import com.example.service.tasktracker.web.model.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User requestToUser(UpsertUserRequest request);

    User requestToUser(String id, UpsertUserRequest request);

    UserResponse userToResponse(User user);
}
