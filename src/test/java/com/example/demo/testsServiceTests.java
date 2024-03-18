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

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class TestsServiceTests {
	@Autowired
    private TestService testService;

    @Test
	void getTest() {
		Assertions.assertThrows(NotFoundException.class, () -> testService.get(0L));
	}

	@Test
	@Order(1)
	void createTest() {
		testService.create(new TestEntity(null, "name", "description", "image"));
		testService.create(new TestEntity(null, "name2", "description2", "image2"));
		final TestEntity last = testService.create(new TestEntity(null, "name3", "description3", "image3"));
		Assertions.assertEquals(3, testService.getAll().size());
		Assertions.assertEquals(last, testService.get(3L));
	}

	@Test
	@Order(2)
	void updateTest() {
		final String test = "TEST";
		final TestEntity entity = testService.get(3L);
		final String oldName = entity.getName();
		final TestEntity newEntity = testService.update(3L, new TestEntity(1L, test, test, test));
		Assertions.assertEquals(3, testService.getAll().size());
		Assertions.assertEquals(newEntity, testService.get(3L));
		Assertions.assertEquals(test, newEntity.getName());
		Assertions.assertNotEquals(oldName, newEntity.getName());
	}

	@Test
	@Order(3)
	void deleteTest() {
		testService.delete(3L);
		Assertions.assertEquals(2, testService.getAll().size());
		final TestEntity last = testService.get(2L);
		Assertions.assertEquals(2L, last.getId());

		final TestEntity newEntity = testService.create(new TestEntity(null, "name4", "description4", "image4"));
		Assertions.assertEquals(3, testService.getAll().size());
		Assertions.assertEquals(4L, newEntity.getId());
	}
}
