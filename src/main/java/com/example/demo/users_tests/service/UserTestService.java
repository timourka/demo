package com.example.demo.users_tests.service;

/*
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.usersTests.model.UserTestEntity;
import com.example.demo.usersTests.repository.UserTestRepository;

@Service
public class UserTestService {
    private final UserTestRepository repository;

    public UserTestService(UserTestRepository repository) {
        this.repository = repository;
    }
    public List<UserTestEntity> getAll(Long testId, Long userId) {
        Stream<UserTestEntity> ret = repository.getAll().stream();

        if (!Objects.equals(testId, 0L))
            ret = ret.filter(item -> item.getTest().getId().equals(testId));
        if (!Objects.equals(userId, 0L))
            ret = ret.filter(item -> item.getUser().getId().equals(userId));

        return ret.toList();
    }

    public UserTestEntity get(Long id) {
        return Optional.ofNullable(repository.get(id))
                .orElseThrow(() -> new NotFoundException(id));
    }

    public UserTestEntity create(UserTestEntity entity) {
        return repository.create(entity);
    }

    public UserTestEntity update(Long id, UserTestEntity entity) {
        final UserTestEntity existsEntity = get(id);
        existsEntity.setScore(entity.getScore());
        existsEntity.setTest(entity.getTest());
        existsEntity.setUser(entity.getUser());
        return repository.update(existsEntity);
    }

    public UserTestEntity delete(Long id) {
        final UserTestEntity existsEntity = get(id);
        return repository.delete(existsEntity);
    }
}
*/