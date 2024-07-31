package com.example.service.tasktracker.web.controller;

import com.example.service.tasktracker.mapper.TaskMapper;
import com.example.service.tasktracker.service.ITaskService;
import com.example.service.tasktracker.web.model.AddObserveRequest;
import com.example.service.tasktracker.web.model.TaskResponse;
import com.example.service.tasktracker.web.model.UpsertTaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final ITaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public Flux<TaskResponse> getAll() {
        return taskService.getAll()
                .map(taskMapper::taskToTaskResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TaskResponse>> get(@PathVariable String id) {
        return taskService.getById(id)
                .map(taskMapper::taskToTaskResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<TaskResponse>> create(@RequestBody UpsertTaskRequest request) {
        return taskService.create(taskMapper.requestToTask(request))
                .map(taskMapper::taskToTaskResponse)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/add-observe/{id}")
    public Mono<ResponseEntity<TaskResponse>> addObserve(@PathVariable String id, @RequestBody AddObserveRequest request) {
        return taskService.addObserve(id, request.getObserveId())
                .map(taskMapper::taskToTaskResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<TaskResponse>> update(@PathVariable String id, @RequestBody UpsertTaskRequest request) {
        return taskService.update(id, taskMapper.requestToTask(id, request))
                .map(taskMapper::taskToTaskResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return taskService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
