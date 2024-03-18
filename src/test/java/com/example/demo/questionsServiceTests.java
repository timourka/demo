package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.core.error.NotFoundException;
import com.example.demo.questions.model.QuestionEntity;
import com.example.demo.questions.service.QuestionService;
import com.example.demo.tests.model.TestEntity;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class QuestionsServiceTests {
	@Autowired
    private QuestionService questionService;

    @Test
    void getTest(){
        Assertions.assertThrows(NotFoundException.class, () -> questionService.get(0L));
    }

    @Test
	@Order(1)
	void createTest() {
		questionService.create(new QuestionEntity(null, new TestEntity(
            null, "name", "description", "image"
        ), "text" , "variant1", "variant2", "variant3", "variant4", "image"));
		questionService.create(new QuestionEntity(null, new TestEntity(
            null, "name", "description", "image"
        ), "text2" , "variant12", "variant22", "variant32", "variant42", "image2"));
		final QuestionEntity last = questionService.create(new QuestionEntity(null, new TestEntity(
            null, "name", "description", "image"
        ), "text3" , "variant13", "variant23", "variant33", "variant43", "image3"));
		Assertions.assertEquals(3, questionService.getAll(0L).size());
		Assertions.assertEquals(last, questionService.get(3L));
	}

	@Test
	@Order(2)
	void updateTest() {
		final String test = "TEST";
		final QuestionEntity entity = questionService.get(3L);
		final String oldText = entity.getText();
		final QuestionEntity newEntity = questionService.update(3L, new QuestionEntity(1L, new TestEntity(
            null, "name", "description", "image"
        ), test, test, test,  test, test, test));
		Assertions.assertEquals(3, questionService.getAll(0L).size());
		Assertions.assertEquals(newEntity, questionService.get(3L));
		Assertions.assertEquals(test, newEntity.getText());
		Assertions.assertNotEquals(oldText, newEntity.getText());
	}

	@Test
	@Order(3)
	void deleteTest() {
		questionService.delete(3L);
		Assertions.assertEquals(2, questionService.getAll(0L).size());
		final QuestionEntity last = questionService.get(2L);
		Assertions.assertEquals(2L, last.getId());

		final QuestionEntity newEntity = questionService.create(new QuestionEntity(null, new TestEntity(
            null, "name", "description", "image"
        ), "Игровая приставка", "Игровая приставка", "Игровая приставка", "Игровая приставка", "Игровая приставка", "Игровая приставка"));
		Assertions.assertEquals(3, questionService.getAll(0L).size());
		Assertions.assertEquals(4L, newEntity.getId());
	}
}
