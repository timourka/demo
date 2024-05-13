package com.example.demo.tests.api;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.example.demo.tests.model.TestEntity;
import com.example.demo.tests.service.TestService;
import com.example.demo.users.model.UserEntity;
import com.example.demo.users.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(TestController.URL)
public class TestController {
    private final Logger log = LoggerFactory.getLogger(TestController.class);
    public static final String URL = Constants.ADMIN_PREFIX + "/test";
    private static final String TEST_VIEW = "test";
    private static final String TEST_EDIT_VIEW = "test-edit";
    private static final String PAGE_ATTRIBUTE = "page";
    private static final String TEST_ATTRIBUTE = "test";

    private final TestService testService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public TestController(ModelMapper modelMapper, TestService testService, UserService userService) {
        this.modelMapper = modelMapper;
        this.testService = testService;
        this.userService = userService;
    }

    private TestDto toDto(TestEntity entity) {
        log.info(modelMapper.map(entity, TestDto.class).getName());
        final TestDto dto = modelMapper.map(entity, TestDto.class);
        dto.setCreaterId(entity.getUserCreator().getId());
        return dto;
    }

    private TestEntity toEntity(TestDto dto) {
        final TestEntity entity = modelMapper.map(dto, TestEntity.class);
        entity.setUserCreator(userService.get(dto.getCreaterId()));
        log.info(userService.get(dto.getCreaterId()).getId().toString());
        log.info(entity.getUserCreator().getId().toString());
        return entity;
    }

    @GetMapping
    public String getAll(
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            Model model) {
        final Map<String, Object> attributes = PageAttributesMapper.toAttributes(
                testService.getAll(0L, page, Constants.DEFUALT_PAGE_SIZE), this::toDto);
        model.addAllAttributes(attributes);
        model.addAttribute(PAGE_ATTRIBUTE, page);
        return TEST_VIEW;
    }

    @GetMapping("/edit/{id}")
    public String update(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            Model model) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        var test = toDto(testService.get(id));
        model.addAttribute(TEST_ATTRIBUTE, test);
        model.addAttribute("createrId", test.getCreaterId());
        model.addAttribute(PAGE_ATTRIBUTE, page);
        return TEST_EDIT_VIEW;
    }

    @PostMapping("/edit/{id}")
    public String update(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            @ModelAttribute(name = TEST_ATTRIBUTE) @Valid TestDto test,
            @ModelAttribute(name = "createrId") @Valid Long createrId,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(PAGE_ATTRIBUTE, page);
            return TEST_EDIT_VIEW;
        }
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        redirectAttributes.addAttribute(PAGE_ATTRIBUTE, page);
        testService.update(id, toEntity(test));
        return Constants.REDIRECT_VIEW + URL;
    }

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute(PAGE_ATTRIBUTE, page);
        testService.delete(id);
        return Constants.REDIRECT_VIEW + URL;
    }
}
