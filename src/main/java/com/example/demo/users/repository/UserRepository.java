package com.example.demo.users.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.users.model.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>, PagingAndSortingRepository<UserEntity, Long>  {
    Optional<UserEntity> findByLoginIgnoreCase(String login);
}
