package com.example.demo;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.core.configuration.Constants;
import com.example.demo.questions.model.QuestionEntity;
import com.example.demo.questions.repository.QuestionRepository;
import com.example.demo.questions.service.QuestionService;
import com.example.demo.tests.model.TestEntity;
import com.example.demo.tests.service.TestService;
import com.example.demo.users.model.UserEntity;
import com.example.demo.users.model.UserRole;
import com.example.demo.users.service.UserService;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    private final UserService userService;
    private final TestService testService;
    private final QuestionService questionService;

    public DemoApplication(UserService userService, TestService testService, QuestionService questionService) {
        this.userService = userService;
        this.testService = testService;
        this.questionService = questionService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // if (args.length > 0 && Objects.equals("--populate", args[0])) {

        log.info("Create default user values");
        var admin = new UserEntity("adm", "adm");
        admin.setRole(UserRole.ADMIN);
        admin = userService.create(admin);
        var user = new UserEntity("user", "user");
        user.setRole(UserRole.USER);
        user = userService.create(user);

        log.info("Create default test values");
        var test = new TestEntity(null, "test", "description", "image", admin);
        test = testService.create(test);
        var test4user = new TestEntity(null, "test", "description", "image", user);
        test4user = testService.create(test4user);
        var testbase = new TestEntity(null, "test1", "description", "image", user);
        List<Long> tests = new ArrayList<Long>();
        for (int i = 0; i < 22; i++) {
            testbase.setName(testbase.getName() + "_");
            tests.add(testService.create(testbase).getId());
            testbase.setId(null);
        }

        log.info("Create default userTest values");
        userService.addUserTests(admin.getId(), test.getId(), 5);
        for (int i = 0; i < 22; i++) {
            userService.addUserTests(user.getId(), tests.get(i), 5);
        }

        log.info("Create default question values");
        var question = new QuestionEntity(null, test, "text", "variant1", "variant2", "variant3", "variant4", "image",
                1);
        question = questionService.create(question);
        var questionBase = new QuestionEntity(null,
                test4user, "text", "variant1", "variant2", "variant3", "variant4",
                "image",
                1);
        for (int i = 0; i < 22; i++) {
            questionBase.setText(question.getText() + "_");
            questionService.create(questionBase);
            questionBase.setId(null);
        }

        // }
    }
}
