package com.example.demo.users.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public class UserDto {
    private final Logger log = LoggerFactory.getLogger(UserDto.class);
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotBlank

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        log.info(name);
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
