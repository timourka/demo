package com.example.demo.tests.api;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.core.api.PageAttributesMapper;
import com.example.demo.core.configuration.Constants;
import com.example.demo.core.security.UserPrincipal;
import com.example.demo.tests.model.TestEntity;
import com.example.demo.tests.service.TestService;
import com.example.demo.users.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;

@Controller
public class MainPageController {
    private final Logger log = LoggerFactory.getLogger(MainPageController.class);
    private static final String MAIN_VIEW = "main";

    private static final String PAGE_ATTRIBUTE = "page";
    private static final String MAIN_ATTRIBUTE = "profile";

    private final TestService testService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public MainPageController(TestService testService, ModelMapper modelMapper, UserService userService) {
        this.testService = testService;
        this.userService = userService;
        this.modelMapper = modelMapper;
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

    @GetMapping()
    public String getMain(@RequestParam(name = PAGE_ATTRIBUTE, defaultValue = "0") int page,
            Model model,
            @AuthenticationPrincipal UserPrincipal principal) {
        model.addAttribute(PAGE_ATTRIBUTE, page);
        model.addAllAttributes(PageAttributesMapper.toAttributes(
                testService.getAll(0L, page, Constants.DEFUALT_PAGE_SIZE),
                this::toDto));
        model.addAttribute("userId", principal.getId());
        return MAIN_VIEW;
    }

}
