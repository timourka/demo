package com.example.demo.core.error;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super(String.format("Entity with id [%s] is not found or not exists", id));
    }
}
