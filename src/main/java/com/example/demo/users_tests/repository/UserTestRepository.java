package com.example.demo.users_tests.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.example.demo.users_tests.model.UserTestEntity;

public interface UserTestRepository extends Repository<UserTestEntity, Long> {
    List<UserTestEntity> findByUserId(Long userId);
    Page<UserTestEntity> findByUserId(long userId, Pageable pageable);
    UserTestEntity save(UserTestEntity userTestEntity);
}
