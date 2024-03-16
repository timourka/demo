package com.example.demo.tests.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.tests.api.TestController;
import com.example.demo.tests.model.TestEntity;
import com.example.demo.tests.repository.TestRepository;

@Service
public class TestService {
    private final Logger log = LoggerFactory.getLogger(TestService.class);
    private final TestRepository repository;

    public TestService(TestRepository repository) {
        this.repository = repository;
    }

    public List<TestEntity> getAll() {
        return repository.getAll();
    }

    public TestEntity get(Long id) {
        return Optional.ofNullable(repository.get(id))
                .orElseThrow(() -> new NotFoundException(id));
    }

    public TestEntity create(TestEntity entity) {
        log.info(entity.getName());
        return repository.create(entity);
    }

    public TestEntity update(Long id, TestEntity entity) {
        final TestEntity existsEntity = get(id);
        existsEntity.setName(entity.getName());
        existsEntity.setDescription(entity.getName());
        existsEntity.setImage(entity.getImage());
        return repository.update(existsEntity);
    }

    public TestEntity delete(Long id) {
        final TestEntity existsEntity = get(id);
        return repository.delete(existsEntity);
    }
}
