package com.example.service.tasktracker.service;

import com.example.service.tasktracker.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface IUserService {

    Flux<User> getAll();
    Mono<User> getById(String id);
    Mono<User> save(User user);
    Mono<User> update(String id, User user);
    Mono<Void> deleteById(String id);
    Flux<User> getAllByIdIn(Set<String> ids);
}
