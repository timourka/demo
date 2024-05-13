package com.example.demo.tests.api;

public class AnserElementDto {
    private boolean active;
    private String text;
    private int id;

    public AnserElementDto() {
    }

    public AnserElementDto(boolean active, String text, int id) {
        this.active = active;
        this.text = text;
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
