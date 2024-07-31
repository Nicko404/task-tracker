package com.example.service.tasktracker.service;

import com.example.service.tasktracker.entity.User;
import com.example.service.tasktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public Flux<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> getById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<User> save(User user) {
        user.setId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    @Override
    public Mono<User> update(String id, User user) {
        return getById(id)
                .flatMap(userForUpdate -> {
                    if (StringUtils.hasText(user.getUsername())) userForUpdate.setUsername(user.getUsername());
                    if (StringUtils.hasText(user.getEmail())) userForUpdate.setEmail(user.getEmail());

                    return userRepository.save(userForUpdate);
                });
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Flux<User> getAllByIdIn(Set<String> ids) {
        return userRepository.findByIdIn(ids);
    }

}
