package com.example.service.tasktracker.service;

import com.example.service.tasktracker.entity.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITaskService {

    Flux<Task> getAll();
    Mono<Task> getById(String id);
    Mono<Task> create(Task task);
    Mono<Task> update(String id, Task task);
    Mono<Task> addObserve(String taskId, String observeId);
    Mono<Void> deleteById(String id);
}
