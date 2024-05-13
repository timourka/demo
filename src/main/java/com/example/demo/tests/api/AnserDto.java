package com.example.demo.tests.api;

import java.util.List;
import java.util.ArrayList;

public class AnserDto {
    private List<AnserElementDto> variants = new ArrayList<>();
    private int value;

    public AnserDto() {
    }

    public AnserDto(List<AnserElementDto> variants, int value) {
        this.variants = variants;
        this.value = value;
    }

    public List<AnserElementDto> getVariants() {
        return variants;
    }

    public void setVariants(List<AnserElementDto> variants) {
        this.variants = variants;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
