package com.example.demo.users.api;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.core.api.PageAttributesMapper;
import com.example.demo.core.configuration.Constants;
import com.example.demo.core.security.UserPrincipal;
import com.example.demo.questions.api.QuestionDto;
import com.example.demo.questions.model.QuestionEntity;
import com.example.demo.questions.service.QuestionService;
import com.example.demo.tests.api.TestDto;
import com.example.demo.tests.model.TestEntity;
import com.example.demo.tests.service.TestService;
import com.example.demo.users.service.UserService;
import com.example.demo.users_tests.model.UserTestEntity;

import jakarta.validation.Valid;

@Controller
@RequestMapping(UserProfileController.URL)
public class UserProfileController {
    private final Logger log = LoggerFactory.getLogger(UserProfileController.class);
    public static final String URL = "/profile";
    private static final String PROFILE_VIEW = "profile";
    private static final String PROFILE_TESTCREATED_VIEW = "profile-testcreated";
    private static final String PROFILE_TESTCREATED_EDIT_VIEW = "profile-testcreated-edit";
    private static final String PROFILE_TESTPASSED_VIEW = "profile-testpassed";
    private static final String PROFILE_QUESTION_VIEW = "profile-question";
    private static final String PROFILE_QUESTION_EDIT_VIEW = "profile-question-edit";

    private static final String PAGE_ATTRIBUTE = "page";
    private static final String TEST_ATTRIBUTE = "test";
    private static final String QUESTION_ATTRIBUTE = "question";
    private static final String TESTID_ATTRIBUTE = "testId";

    private final UserService userService;
    private final TestService testService;
    private final QuestionService questionService;
    private final ModelMapper modelMapper;

    public UserProfileController(UserService userService, TestService testService, QuestionService questionService,
            ModelMapper modelMapper) {
        this.userService = userService;
        this.testService = testService;
        this.questionService = questionService;
        this.modelMapper = modelMapper;
    }

    private TestDto userTestToDto(UserTestEntity entity) {
        TestDto testDto = modelMapper.map(entity.getTest(), TestDto.class);
        testDto.setScore(entity.getScore());
        testDto.setDate(entity.getDate());
        return testDto;
    }

    private TestDto testToDto(TestEntity entity) {
        log.info(modelMapper.map(entity, TestDto.class).getName());
        final TestDto dto = modelMapper.map(entity, TestDto.class);
        dto.setCreaterId(entity.getUserCreator().getId());
        return dto;
    }

    private QuestionDto questionToDto(QuestionEntity entity) {
        return modelMapper.map(entity, QuestionDto.class);
    }

    private TestEntity testToEntity(TestDto dto) {
        final TestEntity entity = modelMapper.map(dto, TestEntity.class);
        entity.setUserCreator(userService.get(dto.getCreaterId()));
        // log.info(userService.get(dto.getCreaterId()).getId().toString());
        // log.info(entity.getUserCreator().getId().toString());
        return entity;
    }

    private QuestionEntity questionToEntity(QuestionDto dto) {
        final QuestionEntity entity = modelMapper.map(dto, QuestionEntity.class);
        entity.setTest(testService.get(dto.getTestId()));
        return entity;
    }

    @GetMapping()
    public String getProfile(Model model) {
        return PROFILE_VIEW;
    }

    @GetMapping("/testsPassed")
    public String gettestsPassed(
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            Model model,
            @AuthenticationPrincipal UserPrincipal principal) {

        model.addAttribute(PAGE_ATTRIBUTE, page);
        final long userId = principal.getId();
        final Map<String, Object> attributes = PageAttributesMapper.toAttributes(
                userService.getUserTests(userId, page, Constants.DEFUALT_PAGE_SIZE), this::userTestToDto);
        model.addAllAttributes(attributes);

        return PROFILE_TESTPASSED_VIEW;
    }

    @GetMapping("/testsCreated")
    public String gettestsCreated(
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            Model model,
            @AuthenticationPrincipal UserPrincipal principal) {

        model.addAttribute(PAGE_ATTRIBUTE, page);
        final long userId = principal.getId();
        final Map<String, Object> attributes = PageAttributesMapper.toAttributes(
                testService.getAll(userId, page, Constants.DEFUALT_PAGE_SIZE), this::testToDto);
        model.addAllAttributes(attributes);

        return PROFILE_TESTCREATED_VIEW;
    }

    @GetMapping("/testsCreated/edit/")
    public String create(
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            Model model) {
        model.addAttribute(TEST_ATTRIBUTE, new TestDto());
        model.addAttribute(PAGE_ATTRIBUTE, page);

        return PROFILE_TESTCREATED_EDIT_VIEW;
    }

    @PostMapping("/testsCreated/edit/")
    public String create(
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            @ModelAttribute(name = TEST_ATTRIBUTE) @Valid TestDto test,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserPrincipal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(PAGE_ATTRIBUTE, page);
            return PROFILE_TESTCREATED_EDIT_VIEW;
        }
        test.setCreaterId(principal.getId());
        redirectAttributes.addAttribute(PAGE_ATTRIBUTE, page);
        testService.create(testToEntity(test));
        return Constants.REDIRECT_VIEW + URL + "/testsCreated";
    }

    @GetMapping("/testsCreated/edit/{id}")
    public String update(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            Model model,
            @AuthenticationPrincipal UserPrincipal principal) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        var test = testToDto(testService.get(id));
        test.setCreaterId(principal.getId());
        model.addAttribute(TEST_ATTRIBUTE, test);
        model.addAttribute(PAGE_ATTRIBUTE, page);
        return PROFILE_TESTCREATED_EDIT_VIEW;
    }

    @PostMapping("/testsCreated/edit/{id}")
    public String update(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            @ModelAttribute(name = TEST_ATTRIBUTE) @Valid TestDto test,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserPrincipal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(PAGE_ATTRIBUTE, page);
            return PROFILE_TESTCREATED_EDIT_VIEW;
        }
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        test.setCreaterId(principal.getId());
        redirectAttributes.addAttribute(PAGE_ATTRIBUTE, page);
        var testEnt = testToEntity(test);
        testService.update(id, testEnt);
        return Constants.REDIRECT_VIEW + URL + "/testsCreated";
    }

    @PostMapping("/testsCreated/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute(PAGE_ATTRIBUTE, page);
        testService.delete(id);
        return Constants.REDIRECT_VIEW + URL + "/testsCreated";
    }

    @GetMapping("questions/{testId}")
    public String getQuestions(
            @PathVariable(name = TESTID_ATTRIBUTE) Long testId,
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            Model model,
            @AuthenticationPrincipal UserPrincipal principal) {

        model.addAttribute(PAGE_ATTRIBUTE, page);
        final Map<String, Object> attributes = PageAttributesMapper.toAttributes(
                questionService.getAll(testId, page, Constants.DEFUALT_PAGE_SIZE), this::questionToDto);
        model.addAllAttributes(attributes);
        model.addAttribute(TESTID_ATTRIBUTE, testId);

        return PROFILE_QUESTION_VIEW;
    }

    @GetMapping("questions/edit/")
    public String createQ(
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            @RequestParam(name = TESTID_ATTRIBUTE, defaultValue = "0") Long testId,
            Model model) {
        var question = new QuestionDto();
        question.setTestId(testId);
        model.addAttribute(QUESTION_ATTRIBUTE, question);
        model.addAttribute(PAGE_ATTRIBUTE, page);
        model.addAttribute(TESTID_ATTRIBUTE, testId);

        return PROFILE_QUESTION_EDIT_VIEW;
    }

    @PostMapping("questions/edit/")
    public String createQ(
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            @RequestParam(name = TESTID_ATTRIBUTE, defaultValue = "0") Long testId,
            @ModelAttribute(name = QUESTION_ATTRIBUTE) @Valid QuestionDto question,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserPrincipal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(PAGE_ATTRIBUTE, page);
            return PROFILE_TESTCREATED_EDIT_VIEW;
        }
        question.setTestId(testId);
        redirectAttributes.addAttribute(PAGE_ATTRIBUTE, page);
        questionService.create(questionToEntity(question));
        return Constants.REDIRECT_VIEW + URL + "/questions/" + testId.toString();
    }

    @GetMapping("questions/edit/{id}")
    public String updateQ(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            @RequestParam(name = TESTID_ATTRIBUTE, defaultValue = "0") Long testId,
            Model model,
            @AuthenticationPrincipal UserPrincipal principal) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        var question = questionToDto(questionService.get(id));
        question.setTestId(testId);
        model.addAttribute(QUESTION_ATTRIBUTE, question);
        model.addAttribute(PAGE_ATTRIBUTE, page);
        model.addAttribute(TESTID_ATTRIBUTE, testId);
        return PROFILE_QUESTION_EDIT_VIEW;
    }

    @PostMapping("questions/edit/{id}")
    public String updateQ(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            @ModelAttribute(name = QUESTION_ATTRIBUTE) @Valid QuestionDto question,
            @RequestParam(name = TESTID_ATTRIBUTE, defaultValue = "0") Long testId,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserPrincipal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(PAGE_ATTRIBUTE, page);
            return PROFILE_TESTCREATED_EDIT_VIEW;
        }
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        question.setTestId(testId);
        redirectAttributes.addAttribute(PAGE_ATTRIBUTE, page);
        questionService.update(id, questionToEntity(question));
        return Constants.REDIRECT_VIEW + URL + "/questions/" + testId.toString();
    }

    @PostMapping("questions/delete/{id}")
    public String deleteQ(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            @RequestParam(name = TESTID_ATTRIBUTE, defaultValue = "0") Long testId,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute(PAGE_ATTRIBUTE, page);
        questionService.delete(id);
        return Constants.REDIRECT_VIEW + URL + "/questions/" + testId.toString();
    }

}
