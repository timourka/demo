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
import com.example.demo.tests.service.TestService;
import com.example.demo.users.model.UserEntity;
import com.example.demo.users.service.UserService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UserTestServiceTest {
	@Autowired
    private UserService userService;

    @Test
	@Order(0)
	void getTest() {
		Assertions.assertThrows(NotFoundException.class, () -> userService.get(0L));
	}

	

	@Test
	@Order(1)
	void createTest() {
		userService.create(new UserEntity(null, "name", "login", "password"));
		userService.create(new UserEntity(null, "name2", "login2", "password2"));
		final UserEntity last = userService.create(new UserEntity(null, "name3", "login3", "password3"));
		Assertions.assertEquals(3, userService.getAll().size());
		Assertions.assertEquals(last, userService.get(3L));
	}

	@Test
	@Order(2)
	void updateTest() {
		final String test = "TEST";
		final UserEntity entity = userService.get(3L);
		final String oldName = entity.getName();
		final UserEntity newEntity = userService.update(3L, new UserEntity(1L, test, test, test));
		Assertions.assertEquals(3, userService.getAll().size());
		Assertions.assertEquals(newEntity, userService.get(3L));
		Assertions.assertEquals(test, newEntity.getName());
		Assertions.assertNotEquals(oldName, newEntity.getName());
	}

	@Test
	@Order(3)
	void deleteTest() {
		userService.delete(3L);
		Assertions.assertEquals(2, userService.getAll().size());
		final UserEntity last = userService.get(2L);
		Assertions.assertEquals(2L, last.getId());

		final UserEntity newEntity = userService.create(new UserEntity(null, "name4", "description4", "image4"));
		Assertions.assertEquals(3, userService.getAll().size());
		Assertions.assertEquals(4L, newEntity.getId());
	}

	@Autowired
    private TestService testService;

    @Test
	@Order(4)
	void getTestTest() {
		Assertions.assertThrows(NotFoundException.class, () -> testService.get(0L));
	}

	@Test
	@Order(5)
	void createTestTest() {
		testService.create(new TestEntity(null, "name", "description", "image", userService.get(2L)));
		testService.create(new TestEntity(null, "name2", "description2", "image2",userService.get(2L)));
		final TestEntity last = testService.create(new TestEntity(null, "name3", "description3", "image3", userService.get(2L)));
		Assertions.assertEquals(3, testService.getAll(0L).size());
		Assertions.assertEquals(last, testService.get(3L));
		Assertions.assertEquals(2L, testService.get(3L).getUserCreator().getId());
	}

	@Test
	@Order(6)
	void updateTestTest() {
		final String test = "TEST";
		final TestEntity entity = testService.get(3L);
		final String oldName = entity.getName();
		final TestEntity newEntity = testService.update(3L, new TestEntity(1L, test, test, test, userService.get(2L)));
		Assertions.assertEquals(3, testService.getAll(0L).size());
		Assertions.assertEquals(newEntity, testService.get(3L));
		Assertions.assertEquals(test, newEntity.getName());
		Assertions.assertNotEquals(oldName, newEntity.getName());
	}

	@Test
	@Order(7)
	void deleteTestTest() {
		testService.delete(3L);
		Assertions.assertEquals(2, testService.getAll(0L).size());
		final TestEntity last = testService.get(2L);
		Assertions.assertEquals(2L, last.getId());

		final TestEntity newEntity = testService.create(new TestEntity(null, "name4", "description4", "image4", userService.get(2L)));
		Assertions.assertEquals(3, testService.getAll(0L).size());
		Assertions.assertEquals(4L, newEntity.getId());
	}

	@Test
	@Order(8)
	void userTestTest(){
		Assertions.assertEquals(0, userService.getUserTests(1L).size());
		userService.addUserTests(1L, 1L, 1);
		Assertions.assertEquals(1, userService.getUserTests(1L).size());
		final var last1 = userService.addUserTests(1L, 1L, 2);
		Assertions.assertEquals(2, userService.getUserTests(1L).size());
		Assertions.assertEquals(0, userService.getUserTests(2L).size());
		final var last2 = userService.addUserTests(2L, 1L, 2);
		Assertions.assertEquals(2, userService.getUserTests(1L).size());
		Assertions.assertEquals(1, userService.getUserTests(2L).size());
		Assertions.assertEquals(last1, userService.getUserTests(1L).get(1));
		Assertions.assertEquals(last2, userService.getUserTests(2L).get(0));
		Assertions.assertEquals(1, testService.minMaxScore(1L).getMin());
		Assertions.assertEquals(2, testService.minMaxScore(1L).getMax());
	}
}
