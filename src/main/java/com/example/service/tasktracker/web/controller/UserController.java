package com.example.service.tasktracker.web.controller;

import com.example.service.tasktracker.mapper.UserMapper;
import com.example.service.tasktracker.service.IUserService;
import com.example.service.tasktracker.web.model.UpsertUserRequest;
import com.example.service.tasktracker.web.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    private final UserMapper userMapper;

    @GetMapping
    public Flux<UserResponse> getAll() {
        return userService.getAll().map(userMapper::userToResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> getById(@PathVariable String id) {
        return userService.getById(id)
                .map(userMapper::userToResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<UserResponse>> create(@RequestBody UpsertUserRequest request)  {
        return userService.save(userMapper.requestToUser(request))
                .map(userMapper::userToResponse)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> update(@PathVariable String id, @RequestBody UpsertUserRequest request) {
        return userService.update(id, userMapper.requestToUser(id, request))
                .map(userMapper::userToResponse)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
        return userService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
