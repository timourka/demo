package com.example.demo.users.api;

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

import com.example.demo.users.model.UserEntity;
import com.example.demo.users.service.UserService;
import com.example.demo.core.configuration.Constants;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Constants.API_URL + "/user")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    private UserDto toDto(UserEntity entity) {
        log.info(modelMapper.map(entity, UserDto.class).getName());
        return modelMapper.map(entity, UserDto.class);
    }

    private UserEntity toEntity(UserDto dto) {
        log.info(modelMapper.map(dto, UserEntity.class).getName());
        return modelMapper.map(dto, UserEntity.class);
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable(name = "id") Long id) {
        return toDto(userService.get(id));
    }

    @PostMapping
    public UserDto create(@RequestBody @Valid UserDto dto) {
        log.info(dto.getName());
        return toDto(userService.create(toEntity(dto)));
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable(name = "id") Long id, @RequestBody UserDto dto) {
        return toDto(userService.update(id, toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public UserDto delete(@PathVariable(name = "id") Long id) {
        return toDto(userService.delete(id));
    }
}
