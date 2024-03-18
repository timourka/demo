package com.example.demo.usersTests.model;

import com.example.demo.core.model.BaseEntity;
import com.example.demo.tests.model.TestEntity;
import com.example.demo.users.model.UserEntity;

public class UserTestEntity  extends BaseEntity {
    private TestEntity test;
    private UserEntity user;
    private int score;
    public UserTestEntity() {
        super();
    }
    public UserTestEntity(Long id, TestEntity test, UserEntity user, int score) {
        super(id);
        this.test = test;
        this.user = user;
        this.score = score;
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
        return true;
    }
    

}
