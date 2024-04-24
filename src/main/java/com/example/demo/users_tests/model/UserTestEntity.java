package com.example.demo.users_tests.model;

import java.util.Date;

import com.example.demo.core.model.BaseEntity;
import com.example.demo.tests.model.TestEntity;
import com.example.demo.users.model.UserEntity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_tests")
public class UserTestEntity  extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "testId", nullable = false)
    private TestEntity test;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;
    private int score;
    private Date date;
    public UserTestEntity() {
        super();
        date = new Date();
    }
    public UserTestEntity(Long id, TestEntity test, UserEntity user, int score) {
        super(id);
        this.test = test;
        this.user = user;
        this.score = score;
        date = new Date();
    }
    public TestEntity getTest() {
        return test;
    }
    public UserEntity getUser() {
        return user;
    }
    public int getScore() {
        return score;
    }
    public Date getDate() {
        return date;
    }
    public void setTest(TestEntity test) {
        this.test = test;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }
    public void setScore(int score) {
        this.score = score;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((test == null) ? 0 : test.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + score;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
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
        UserTestEntity other = (UserTestEntity) obj;
        if (test == null) {
            if (other.test != null)
                return false;
        } else if (!test.equals(other.test))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        if (score != other.score)
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        return true;
    }
       

}
