package com.example.demo.usersTests.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UserTestDto {
    private Long id;
    @NotNull
    @Min(1)
    private Long testId;
    @NotNull
    @Min(1)
    private Long userId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
        return id;
    }

    public Long getTestId() {
        return testId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
}
