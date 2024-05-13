package com.example.demo.tests.api;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TestDto {
    private final Logger log = LoggerFactory.getLogger(TestDto.class);
    private Long id;
    private Long createrId;
    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private String image;

    private int score;
    private Date date;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public int getScore() {
        return score;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Date getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        log.info(name);
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

}
