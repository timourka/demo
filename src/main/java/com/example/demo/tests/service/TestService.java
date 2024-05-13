package com.example.demo.tests.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.tests.model.MinMax;
import com.example.demo.tests.model.TestEntity;
import com.example.demo.tests.repository.TestRepository;

@Service
public class TestService {
    private final Logger log = LoggerFactory.getLogger(TestService.class);
    private final TestRepository repository;

    public TestService(TestRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<TestEntity> getAll(Long userId) {
        if (userId < 1L)
            return StreamSupport.stream(repository.findAll().spliterator(), false).toList();
        return repository.findByUserCreatorId(userId);
    }

    @Transactional(readOnly = true)
    public Page<TestEntity> getAll(Long userId, int page, int size) {
        final Pageable pageRequest = PageRequest.of(page, size);
        if (userId < 1L)
            return repository.findAll(pageRequest);
        return repository.findByUserCreatorId(userId, pageRequest);
    }

    @Transactional(readOnly = true)
    public TestEntity get(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(TestEntity.class, id));
    }

    @Transactional
    public TestEntity create(TestEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity is null");
        }
        return repository.save(entity);
    }

    @Transactional
    public TestEntity update(Long id, TestEntity entity) {
        final TestEntity existsEntity = get(id);
        existsEntity.setName(entity.getName());
        existsEntity.setDescription(entity.getDescription());
        existsEntity.setImage(entity.getImage());
        return repository.save(existsEntity);
    }

    @Transactional
    public TestEntity delete(Long id) {
        final TestEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }

    @Transactional
    public MinMax minMaxScore(Long id) {
        return repository.getMinMaxScore(id);
    }
}
