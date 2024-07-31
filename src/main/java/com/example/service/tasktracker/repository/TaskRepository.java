package com.example.service.tasktracker.repository;


import com.example.service.tasktracker.entity.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TaskRepository extends ReactiveMongoRepository<Task, String> {

}
