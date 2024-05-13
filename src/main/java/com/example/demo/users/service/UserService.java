package com.example.demo.users.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.demo.core.configuration.Constants;
import com.example.demo.core.error.NotFoundException;
import com.example.demo.core.security.UserPrincipal;
import com.example.demo.tests.service.TestService;
import com.example.demo.users.model.UserEntity;
import com.example.demo.users.model.UserRole;
import com.example.demo.users.repository.UserRepository;
import com.example.demo.users_tests.model.UserTestEntity;
import com.example.demo.users_tests.repository.UserTestRepository;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final TestService testService;
    private final UserTestRepository userTestRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository repository,
            PasswordEncoder passwordEncoder,
            TestService testService,
            UserTestRepository userTestRepository) {
        this.repository = repository;
        this.testService = testService;
        this.userTestRepository = userTestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void checkLogin(Long id, String login) {
        final Optional<UserEntity> existsUser = repository.findByLoginIgnoreCase(login);
        if (existsUser.isPresent() && !existsUser.get().getId().equals(id)) {
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
        return repository.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

    @Transactional(readOnly = true)
    public UserEntity get(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(UserEntity.class, id));
    }

    @Transactional(readOnly = true)
    public UserEntity getByLogin(String login) {
        return repository.findByLoginIgnoreCase(login)
                .orElseThrow(() -> new IllegalArgumentException("Invalid login"));
    }

    @Transactional
    public UserEntity create(UserEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity is null");
        }
        checkLogin(null, entity.getLogin());
        final String password = Optional.ofNullable(entity.getPassword()).orElse("");
        entity.setPassword(
                passwordEncoder.encode(
                        StringUtils.hasText(password.strip()) ? password : Constants.DEFAULT_PASSWORD));
        entity.setRole(Optional.ofNullable(entity.getRole()).orElse(UserRole.USER));
        return repository.save(entity);
    }

    @Transactional
    public UserEntity update(long id, UserEntity entity) {
        final UserEntity existsEntity = get(id);
        checkLogin(id, entity.getLogin());
        existsEntity.setLogin(entity.getLogin());
        repository.save(existsEntity);
        return existsEntity;
    }

    @Transactional
    public UserEntity delete(long id) {
        final UserEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity existsUser = getByLogin(username);
        return new UserPrincipal(existsUser);
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
