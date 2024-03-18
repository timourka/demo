package com.example.demo.usersTests.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.core.repository.MapRepository;
import com.example.demo.usersTests.model.UserTestEntity;

@Repository
public class UserTestRepository extends MapRepository<UserTestEntity> {

}
