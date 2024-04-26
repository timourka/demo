package com.example.demo.tests.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.tests.model.MinMax;
import com.example.demo.tests.model.TestEntity;

@Repository
public interface TestRepository extends CrudRepository<TestEntity, Long>, PagingAndSortingRepository<TestEntity, Long> {
    List<TestEntity> findByUserCreatorId(Long user);
    Page<TestEntity> findByUserCreatorId(Long user, Pageable pageable);

    @Query("SELECT MIN(ut.score) as min, MAX(ut.score) as max "
    + "FROM UserTestEntity ut "
    + "WHERE ut.test.id = ?1"
    )
    MinMax getMinMaxScore(Long id);
}
