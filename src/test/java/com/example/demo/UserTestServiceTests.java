package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.tests.model.TestEntity;
import com.example.demo.users.model.UserEntity;
import com.example.demo.usersTests.model.UserTestEntity;
import com.example.demo.usersTests.service.UserTestService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UserTestServiceTests {
	@Autowired
    private UserTestService userTestService;
    
    @Test
	void getTest() {
		Assertions.assertThrows(NotFoundException.class, () -> userTestService.get(0L));
	}
    
	@Test
	@Order(1)
	void createTest() {
		userTestService.create(new UserTestEntity(null, new TestEntity(
            null, "name", "description", "image"
        ), new UserEntity(
            null, "name", "login", "password"
        ), 111));
		userTestService.create(new UserTestEntity(null, new TestEntity(
            null, "name2", "description2", "image2"
        ), new UserEntity(
            null, "name2", "login2", "password2"
        ), 222));
		final UserTestEntity last = userTestService.create(new UserTestEntity(null, new TestEntity(
            null, "name3", "description3", "image3"
        ), new UserEntity(
            null, "name3", "login3", "password3"
        ), 333));
		Assertions.assertEquals(3, userTestService.getAll(0L,0L).size());
		Assertions.assertEquals(last, userTestService.get(3L));
	}

	@Test
	@Order(2)
	void updateTest() {
		final int test = 123;
		final UserTestEntity entity = userTestService.get(3L);
		final int oldScore = entity.getScore();
		final UserTestEntity newEntity = userTestService.update(3L, new UserTestEntity(1L, new TestEntity(
            null, "name", "description", "image"
        ), new UserEntity(
            null, "name", "login", "password"
        ), test));
		Assertions.assertEquals(3, userTestService.getAll(0L,0L).size());
		Assertions.assertEquals(newEntity, userTestService.get(3L));
		Assertions.assertEquals(test, newEntity.getScore());
		Assertions.assertNotEquals(oldScore, newEntity.getScore());
	}

	@Test
	@Order(3)
	void deleteTest() {
		userTestService.delete(3L);
		Assertions.assertEquals(2, userTestService.getAll(0L,0L).size());
		final UserTestEntity last = userTestService.get(2L);
		Assertions.assertEquals(2L, last.getId());

		final UserTestEntity newEntity = userTestService.create(new UserTestEntity(null, new TestEntity(
            null, "name4", "description4", "image4"
        ), new UserEntity(
            null, "name4", "login4", "password4"
        ), 444));
		Assertions.assertEquals(3, userTestService.getAll(0L,0L).size());
		Assertions.assertEquals(4L, newEntity.getId());
	}
}
