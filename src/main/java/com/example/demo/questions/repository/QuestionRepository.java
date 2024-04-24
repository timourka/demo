package com.example.demo.questions.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.questions.model.QuestionEntity;

@Repository
public interface QuestionRepository extends CrudRepository<QuestionEntity, Long>, PagingAndSortingRepository<QuestionEntity, Long>  {
    List<QuestionEntity> findByTestId(Long test);
    Page<QuestionEntity> findByTestId(Long test, Pageable pageable);
}
