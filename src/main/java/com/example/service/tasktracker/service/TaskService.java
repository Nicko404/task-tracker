package com.example.service.tasktracker.service;

import com.example.service.tasktracker.entity.Task;
import com.example.service.tasktracker.entity.User;
import com.example.service.tasktracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;
    private final IUserService userService;

    @Override
    public Flux<Task> getAll() {
        Flux<Task> taskFlux = taskRepository.findAll();

        return Flux.zip(taskFlux,
                        taskFlux.flatMap(task -> userService.getById(task.getAuthorId())),
                        taskFlux.flatMap(task -> userService.getById(task.getAssigneeId())),
                        taskFlux.flatMap(task -> userService.getAllByIdIn(task.getObserversIds()).collectList())
                ).map(data -> {
                    data.getT1().setAuthor(data.getT2());
                    data.getT1().setAssignee(data.getT3());
                    data.getT4().forEach(data.getT1()::addObserver);
                    return data.getT1();
                });
    }

    @Override
    public Mono<Task> getById(String id) {
        Mono<Task> taskMono = taskRepository.findById(id);
        Mono<User> authorMono = taskMono.flatMap(task -> userService.getById(task.getAuthorId()));
        Mono<User> assigneeMono = taskMono.flatMap(task -> userService.getById(task.getAssigneeId()));
        Mono<List<User>> obseversListMono = taskMono.flatMap(task -> userService.getAllByIdIn(task.getObserversIds()).collectList());

        return Mono.zip(taskMono, authorMono, assigneeMono, obseversListMono)
                .map(data -> {
                    data.getT1().setAuthor(data.getT2());
                    data.getT1().setAssignee(data.getT3());
                    data.getT1().setObservers(new HashSet<>(data.getT4()));
                    return data.getT1();
                });
    }

    @Override
    public Mono<Task> create(Task task) {
        task.setId(UUID.randomUUID().toString());
        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());

        Mono<Task> created = taskRepository.save(task);
        Mono<User> authorMono = userService.getById(task.getAuthorId());
        Mono<User> assigneeMono = userService.getById(task.getAssigneeId());
        Flux<User> observersFlux = userService.getAllByIdIn(task.getObserversIds());

        return Mono.zip(created, authorMono, assigneeMono, observersFlux.collectList())
                .map(data -> {
                    data.getT1().setAuthor(data.getT2());
                    data.getT1().setAssignee(data.getT3());
                    data.getT4().forEach(data.getT1()::addObserver);

                    return data.getT1();
                });
    }

    @Override
    public Mono<Task> update(String id, Task task) {
        return getById(id)
                .flatMap(taskForUpdate -> {
                    if (StringUtils.hasText(task.getName())) taskForUpdate.setName(task.getName());
                    if (StringUtils.hasText(task.getDescription())) taskForUpdate.setDescription(task.getDescription());
                    if (Objects.nonNull(task.getStatus())) taskForUpdate.setStatus(task.getStatus());
                    if (StringUtils.hasText(task.getAuthorId())) taskForUpdate.setAuthorId(task.getAuthorId());
                    if (StringUtils.hasText(task.getAssigneeId())) taskForUpdate.setAssigneeId(task.getAssigneeId());
                    taskForUpdate.setUpdatedAt(Instant.now());

                    return taskRepository.save(taskForUpdate);
                });
    }

    @Override
    public Mono<Task> addObserve(String taskId, String observeId) {
        Mono<Task> taskMono = getById(taskId);

        return Mono.zip(taskMono, taskMono.flatMap(task -> {
            task.addObserverId(observeId);
            return taskRepository.save(task);
        })).map(Tuple2::getT2);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return taskRepository.deleteById(id);
    }
}
