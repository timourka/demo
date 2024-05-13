package com.example.demo.questions.api;

import java.util.Map;

import org.modelmapper.ModelMapper;
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
import com.example.demo.questions.model.QuestionEntity;
import com.example.demo.questions.service.QuestionService;
import com.example.demo.tests.service.TestService;
import com.example.demo.users.model.UserEntity;
import com.example.demo.users.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping(QuestionController.URL)
public class QuestionController {
    public static final String URL = Constants.ADMIN_PREFIX + "/question";
    private static final String QUESTION_VIEW = "question";
    private static final String QUESTION_EDIT_VIEW = "question-edit";
    private static final String PAGE_ATTRIBUTE = "page";
    private static final String TESTID_ATRIBUTE = "testId";
    private static final String QUESTTION_ATTRIBUTE = "question";

    private final QuestionService questionService;
    private final TestService testService;
    private final ModelMapper modelMapper;

    public QuestionController(ModelMapper modelMapper, QuestionService questionService, TestService testService) {
        this.modelMapper = modelMapper;
        this.questionService = questionService;
        this.testService = testService;
    }

    private QuestionDto toDto(QuestionEntity entity) {
        return modelMapper.map(entity, QuestionDto.class);
    }

    private QuestionEntity toEntity(QuestionDto dto) {
        final QuestionEntity entity = modelMapper.map(dto, QuestionEntity.class);
        entity.setTest(testService.get(dto.getTestId()));
        return entity;
    }

    @GetMapping
    public String getAll(
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            @RequestParam(name = TESTID_ATRIBUTE, defaultValue = "0") Long testId,
            Model model) {
        final Map<String, Object> attributes = PageAttributesMapper.toAttributes(
                questionService.getAll(testId, page, Constants.DEFUALT_PAGE_SIZE), this::toDto);
        model.addAllAttributes(attributes);
        model.addAttribute("tests", testService.getAll(0L));
        model.addAttribute(PAGE_ATTRIBUTE, page);
        model.addAttribute(TESTID_ATRIBUTE, testId);
        return QUESTION_VIEW;
    }

    @GetMapping("/edit/{id}")
    public String update(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            Model model) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        var question = toDto(questionService.get(id));
        model.addAttribute(QUESTTION_ATTRIBUTE, question);
        model.addAttribute(PAGE_ATTRIBUTE, page);
        model.addAttribute(TESTID_ATRIBUTE, question.getTestId());
        return QUESTION_EDIT_VIEW;
    }

    @PostMapping("/edit/{id}")
    public String update(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            @ModelAttribute(name = QUESTTION_ATTRIBUTE) @Valid QuestionDto user,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(PAGE_ATTRIBUTE, page);
            return QUESTION_EDIT_VIEW;
        }
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        redirectAttributes.addAttribute(PAGE_ATTRIBUTE, page);
        questionService.update(id, toEntity(user));
        return Constants.REDIRECT_VIEW + URL;
    }

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute(PAGE_ATTRIBUTE, page);
        questionService.delete(id);
        return Constants.REDIRECT_VIEW + URL;
    }

}
