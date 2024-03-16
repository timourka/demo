package com.example.demo.questions.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.core.repository.MapRepository;
import com.example.demo.questions.model.QuestionEntity;

@Repository
public class QuestionRepository extends MapRepository<QuestionEntity> {

}
