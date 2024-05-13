package com.example.demo.questions.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QuestionDto {
    private Long id;
    @NotNull
    @Min(1)
    private Long testId;
    @NotBlank
    private String text;
    @NotBlank
    private String variant1;
    @NotBlank
    private String variant2;
    @NotBlank
    private String variant3;
    @NotBlank
    private String variant4;
    @NotBlank
    private String image;
    @NotNull
    @Min(1)
    @Max(4)
    private int rightAnser;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
        return id;
    }

    public Long getTestId() {
        return testId;
    }

    public String getText() {
        return text;
    }

    public String getVariant1() {
        return variant1;
    }

    public String getVariant2() {
        return variant2;
    }

    public String getVariant3() {
        return variant3;
    }

    public String getVariant4() {
        return variant4;
    }

    public String getImage() {
        return image;
    }

    public int getRightAnser() {
        return rightAnser;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setVariant1(String variant1) {
        this.variant1 = variant1;
    }

    public void setVariant2(String variant2) {
        this.variant2 = variant2;
    }

    public void setVariant3(String variant3) {
        this.variant3 = variant3;
    }

    public void setVariant4(String variant4) {
        this.variant4 = variant4;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRightAnser(int rightAnser) {
        this.rightAnser = rightAnser;
    }
}
