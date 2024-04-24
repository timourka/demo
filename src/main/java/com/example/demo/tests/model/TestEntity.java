package com.example.demo.tests.model;

import com.example.demo.core.model.BaseEntity;
import com.example.demo.users.model.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tests")
public class TestEntity extends BaseEntity  {
    @Column(nullable = false, unique = false, length = 50)
    private String name;
    @Column(nullable = false, unique = false, length = 50)
    private String description;
    @Column(nullable = false, unique = false, length = 5000)
    private String image;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity userCreator;

    public TestEntity() {
        super();
    }

    public TestEntity(Long id, String name, String description, String image, UserEntity userCreator) {
        super(id);
        this.name = name;
        this.description = description;
        this.image = image;
        this.userCreator = userCreator;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUserCreator(UserEntity userCreator) {
        this.userCreator = userCreator;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public UserEntity getUserCreator() {
        return userCreator;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((image == null) ? 0 : image.hashCode());
        result = prime * result + ((userCreator == null) ? 0 : userCreator.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TestEntity other = (TestEntity) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (image == null) {
            if (other.image != null)
                return false;
        } else if (!image.equals(other.image))
            return false;
        if (userCreator == null) {
            if (other.userCreator != null)
                return false;
        } else if (!userCreator.equals(other.userCreator))
            return false;
        return true;
    }

    
    
}
