package com.example.demo.questions.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.questions.model.QuestionEntity;
import com.example.demo.questions.repository.QuestionRepository;

@Service
public class QuestionService {
    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<QuestionEntity> getAll(Long testId) {
        if (testId < 1L){
            return StreamSupport.stream(repository.findAll().spliterator(), false).toList();
        }
        return repository.findByTestId(testId);
    }

    @Transactional(readOnly = true)
    public Page<QuestionEntity> getAll(Long testId, int page, int size) {
        final Pageable pageRequest = PageRequest.of(page, size);
        if (testId < 1L){
            return repository.findAll(pageRequest);
        }
        return repository.findByTestId(testId, pageRequest);
    }

    @Transactional(readOnly = true)
    public QuestionEntity get(Long id) {
        return repository.findById(id)
        .orElseThrow(() -> new NotFoundException(QuestionEntity.class, id));
    }

    @Transactional
    public QuestionEntity create(QuestionEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity is null");
        }
        return repository.save(entity);
    }

    @Transactional
    public QuestionEntity update(Long id, QuestionEntity entity) {
        final QuestionEntity existsEntity = get(id);
        existsEntity.setImage(entity.getImage());
        existsEntity.setTest(entity.getTest());
        existsEntity.setText(entity.getText());
        existsEntity.setVariant1(entity.getVariant1());
        existsEntity.setVariant2(entity.getVariant2());
        existsEntity.setVariant3(entity.getVariant3());
        existsEntity.setVariant4(entity.getVariant4());
        return repository.save(existsEntity);
    }

    @Transactional
    public QuestionEntity delete(Long id) {
        final QuestionEntity existsEntity = get(id);
        repository.delete(existsEntity);
        return existsEntity;
    }
}
