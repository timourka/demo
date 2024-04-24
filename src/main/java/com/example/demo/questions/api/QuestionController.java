package com.example.demo.questions.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.core.api.PageDto;
import com.example.demo.core.api.PageDtoMapper;
import com.example.demo.core.configuration.Constants;
import com.example.demo.questions.model.QuestionEntity;
import com.example.demo.questions.service.QuestionService;
import com.example.demo.tests.service.TestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Constants.API_URL + "/question")
public class QuestionController {
    private final QuestionService questionService;
    private final TestService testService;
    private final ModelMapper modelMapper;

    public QuestionController(QuestionService questionService, TestService testService, ModelMapper modelMapper) {
        this.questionService = questionService;
        this.testService = testService;
        this.modelMapper = modelMapper;
    }

    private QuestionDto toDto(QuestionEntity entity) {
        return modelMapper.map(entity, QuestionDto.class);
    }

    private QuestionEntity toEntity(QuestionDto dto) {
        final QuestionEntity entity = modelMapper.map(dto, QuestionEntity.class);
        entity.setTest(testService.get(dto.getTestId()));
        return entity;
    }

    @GetMapping
    public PageDto<QuestionDto> getAll(
        @RequestParam(name = "testId", defaultValue = "0") Long testId,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size
    ) {
        return PageDtoMapper.toDto(questionService.getAll(testId, page, size), this::toDto);
    }

    @GetMapping("/{id}")
    public QuestionDto get(@PathVariable(name = "id") Long id) {
        return toDto(questionService.get(id));
    }

    @PostMapping
    public QuestionDto create(@RequestBody @Valid QuestionDto dto) {
        return toDto(questionService.create(toEntity(dto)));
    }

    @PutMapping("/{id}")
    public QuestionDto update(@PathVariable(name = "id") Long id, @RequestBody QuestionDto dto) {
        return toDto(questionService.update(id, toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public QuestionDto delete(@PathVariable(name = "id") Long id) {
        return toDto(questionService.delete(id));
    }
}
