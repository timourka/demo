package com.example.demo.tests.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.tests.model.TestEntity;
import com.example.demo.tests.service.TestService;
import com.example.demo.core.configuration.Constants;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Constants.API_URL + "/test")
public class TestController {
    private final Logger log = LoggerFactory.getLogger(TestController.class);
    private final TestService testService;
    private final ModelMapper modelMapper;

    public TestController(TestService testService, ModelMapper modelMapper) {
        this.testService = testService;
        this.modelMapper = modelMapper;
    }

    private TestDto toDto(TestEntity entity) {
        log.info(modelMapper.map(entity, TestDto.class).getName());
        return modelMapper.map(entity, TestDto.class);
    }

    private TestEntity toEntity(TestDto dto) {
        log.info(modelMapper.map(dto, TestEntity.class).getName());
        return modelMapper.map(dto, TestEntity.class);
    }

    @GetMapping
    public List<TestDto> getAll() {
        return testService.getAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public TestDto get(@PathVariable(name = "id") Long id) {
        return toDto(testService.get(id));
    }

    @PostMapping
    public TestDto create(@RequestBody @Valid TestDto dto) {
        log.info(dto.getName());
        return toDto(testService.create(toEntity(dto)));
    }

    @PutMapping("/{id}")
    public TestDto update(@PathVariable(name = "id") Long id, @RequestBody TestDto dto) {
        return toDto(testService.update(id, toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public TestDto delete(@PathVariable(name = "id") Long id) {
        return toDto(testService.delete(id));
    }
}
