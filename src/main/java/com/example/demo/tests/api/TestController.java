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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.tests.model.MinMax;
import com.example.demo.tests.model.TestEntity;
import com.example.demo.tests.service.TestService;
import com.example.demo.users.service.UserService;
import com.example.demo.core.api.PageDto;
import com.example.demo.core.api.PageDtoMapper;
import com.example.demo.core.configuration.Constants;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Constants.API_URL + "/test")
public class TestController {
    private final Logger log = LoggerFactory.getLogger(TestController.class);
    private final TestService testService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public TestController(TestService testService, ModelMapper modelMapper, UserService userService) {
        this.testService = testService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    private TestDto toDto(TestEntity entity) {
        log.info(modelMapper.map(entity, TestDto.class).getName());
        final TestDto dto = modelMapper.map(entity, TestDto.class);
        dto.setCreaterId(entity.getUserCreator().getId());
        return dto;
    }

    private TestEntity toEntity(TestDto dto) {
        final TestEntity entity = modelMapper.map(dto, TestEntity.class);
        entity.setUserCreator(userService.get(dto.getCreaterId()));
        log.info(userService.get(dto.getCreaterId()).getId().toString());
        log.info(entity.getUserCreator().getId().toString());
        return entity;
    }

    @GetMapping
    public PageDto<TestDto> getAll(
        @RequestParam(name = "userId", defaultValue = "0") Long userId,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size
        ) {
        return PageDtoMapper.toDto(testService.getAll(userId, page, size), this::toDto);
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

    @GetMapping("/minMaxScore/{id}")
    public MinMax getMinMax(@PathVariable(name = "id") Long id) {
        return testService.minMaxScore(id);
    }
    
}
