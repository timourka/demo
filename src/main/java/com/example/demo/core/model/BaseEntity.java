package com.example.demo.core.model;

import com.example.demo.core.configuration.Constants;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue()
    @SequenceGenerator(name = Constants.SEQUENCE_NAME, sequenceName = Constants.SEQUENCE_NAME, allocationSize = 1)
    protected Long id;

    protected BaseEntity() {
    }

    protected BaseEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
