package com.example.demo.usersTests.api;

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

import com.example.demo.core.configuration.Constants;
import com.example.demo.tests.service.TestService;
import com.example.demo.users.service.UserService;
import com.example.demo.usersTests.model.UserTestEntity;
import com.example.demo.usersTests.service.UserTestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Constants.API_URL + "/userTest")
public class UserTestController {
    private final UserTestService userTestService;
    private final TestService testService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserTestController(UserTestService userTestService, TestService testService, UserService userService,
            ModelMapper modelMapper) {
        this.userTestService = userTestService;
        this.testService = testService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    private UserTestDto toDto(UserTestEntity entity) {
        return modelMapper.map(entity, UserTestDto.class);
    }

    private UserTestEntity toEntity(UserTestDto dto) {
        final UserTestEntity entity = modelMapper.map(dto, UserTestEntity.class);
        entity.setTest(testService.get(dto.getTestId()));
        entity.setUser(userService.get(dto.getUserId()));
        return entity;
    }

    @GetMapping
    public List<UserTestDto> getAll(@RequestParam(name = "testId", defaultValue = "0") Long testId, @RequestParam(name = "userId", defaultValue = "0") Long userId) {
        return userTestService.getAll(testId, userId).stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public UserTestDto get(@PathVariable(name = "id") Long id) {
        return toDto(userTestService.get(id));
    }

    @PostMapping
    public UserTestDto create(@RequestBody @Valid UserTestDto dto) {
        return toDto(userTestService.create(toEntity(dto)));
    }

    @PutMapping("/{id}")
    public UserTestDto update(@PathVariable(name = "id") Long id, @RequestBody UserTestDto dto) {
        return toDto(userTestService.update(id, toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public UserTestDto delete(@PathVariable(name = "id") Long id) {
        return toDto(userTestService.delete(id));
    }
}
