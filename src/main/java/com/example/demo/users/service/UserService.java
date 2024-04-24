package com.example.demo.users.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.tests.service.TestService;
import com.example.demo.users.model.UserEntity;
import com.example.demo.users.repository.UserRepository;
import com.example.demo.users_tests.model.UserTestEntity;
import com.example.demo.users_tests.repository.UserTestRepository;

@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository repository;
    private final TestService testService;
    private final UserTestRepository userTestRepository;

    public UserService(UserRepository repository, TestService testService, UserTestRepository userTestRepository) {
        this.repository = repository;
        this.testService = testService;
        this.userTestRepository = userTestRepository;
    }

    private void checkLogin(String login) {
        if (repository.findByLoginIgnoreCase(login).isPresent()) {
            throw new IllegalArgumentException(
                    String.format("User with login %s is already exists", login));
        }
    }

    @Transactional(readOnly = true)
    public List<UserEntity> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).toList();
    }

    @Transactional(readOnly = true)
    public Page<UserEntity> getAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    @Transactional(readOnly = true)
    public UserEntity get(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException(UserEntity.class, id));
    }

    @Transactional
    public UserEntity create(UserEntity entity) {
        log.info(entity.getName());
        if (entity == null) {
            throw new IllegalArgumentException("Entity is null");
        }
        checkLogin(entity.getLogin());
        return repository.save(entity);
    }

    @Transactional
    public UserEntity update(Long id, UserEntity entity) {
        final UserEntity existsEntity = get(id);
        existsEntity.setName(entity.getName());
        existsEntity.setLogin(entity.getLogin());
        existsEntity.setPassword(entity.getPassword());
        return repository.save(existsEntity);
    }

    @Transactional
    public UserEntity delete(Long id) {
        final UserEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }
    
    @Transactional(readOnly = true)
    public List<UserTestEntity> getUserTests(long id) {
        return userTestRepository.findByUserId(id);
    }
    
    @Transactional
    public UserTestEntity addUserTests(long id, long testId, int score) {
        UserTestEntity userTestEntity = new UserTestEntity();
        userTestEntity.setScore(score);
        userTestEntity.setTest(testService.get(testId));
        userTestEntity.setUser(get(id));
        userTestRepository.save(userTestEntity);
        return userTestEntity;
    }
    
    @Transactional(readOnly = true)
    public Page<UserTestEntity> getUserTests(long id, int page, int size) {
        return userTestRepository.findByUserId(id, PageRequest.of(page, size));
    }
}
