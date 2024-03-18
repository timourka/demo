package com.example.demo.users.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.core.repository.MapRepository;
import com.example.demo.users.model.UserEntity;

@Repository
public class UserRepository extends MapRepository<UserEntity> {
}
