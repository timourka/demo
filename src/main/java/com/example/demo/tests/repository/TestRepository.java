package com.example.demo.tests.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.core.repository.MapRepository;
import com.example.demo.tests.model.TestEntity;

@Repository
public class TestRepository extends MapRepository<TestEntity> {
}
