package com.example.demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestionDTO {
    private int testID;
    private String text;
    private String variant1;
    private String variant2;
    private String variant3;
    private String variant4;
    private String image;
    private int id;

    public QuestionDTO() {
    }
    
    @JsonCreator
    public QuestionDTO(
            @JsonProperty(value = "testID") int testID,
            @JsonProperty(value = "text") String text,
            @JsonProperty(value = "variant1") String variant1,
            @JsonProperty(value = "variant2") String variant2,
            @JsonProperty(value = "variant3") String variant3,
            @JsonProperty(value = "variant4") String variant4,
            @JsonProperty(value = "image") String image,
            @JsonProperty(value = "id") int id) {
        this.testID = testID;
        this.text = text;
        this.variant1 = variant1;
        this.variant2 = variant2;
        this.variant3 = variant3;
        this.variant4 = variant4;
        this.image = image;
        this.id = id;
    }

    public int getTestID() {
        return testID;
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

    public int getId() {
        return id;
    }
    
}