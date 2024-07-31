package com.example.service.tasktracker.repository;

import com.example.service.tasktracker.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.Set;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Flux<User> findByIdIn(Set<String> ids);
}
