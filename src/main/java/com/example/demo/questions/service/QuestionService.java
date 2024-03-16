package com.example.demo.questions.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.questions.model.QuestionEntity;
import com.example.demo.questions.repository.QuestionRepository;

@Service
public class QuestionService {
    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }
    public List<QuestionEntity> getAll(Long testId) {
        if (Objects.equals(testId, 0L)) {
            return repository.getAll();
        }
        return repository.getAll().stream()
                .filter(item -> item.getTest().getId().equals(testId))
                .toList();
    }

    public QuestionEntity get(Long id) {
        return Optional.ofNullable(repository.get(id))
                .orElseThrow(() -> new NotFoundException(id));
    }

    public QuestionEntity create(QuestionEntity entity) {
        return repository.create(entity);
    }

    public QuestionEntity update(Long id, QuestionEntity entity) {
        final QuestionEntity existsEntity = get(id);
        existsEntity.setImage(entity.getImage());
        existsEntity.setTest(entity.getTest());
        existsEntity.setText(entity.getText());
        existsEntity.setVariant1(entity.getVariant1());
        existsEntity.setVariant2(entity.getVariant2());
        existsEntity.setVariant3(entity.getVariant3());
        existsEntity.setVariant4(entity.getVariant4());
        return repository.update(existsEntity);
    }

    public QuestionEntity delete(Long id) {
        final QuestionEntity existsEntity = get(id);
        return repository.delete(existsEntity);
    }
}
