package com.example.demo.tests.api;

import java.util.ArrayList;
import java.util.Map;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.core.api.PageAttributesMapper;
import com.example.demo.core.configuration.Constants;
import com.example.demo.core.security.UserPrincipal;
import com.example.demo.questions.service.QuestionService;
import com.example.demo.users.model.UserEntity;
import com.example.demo.users.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping(TestInProcess.URL)
@SessionAttributes({ TestInProcess.RIGHT_ATRIBUTE, TestInProcess.WRONG_ATRIBUTE })
public class TestInProcess {
    public static final String URL = "/testInProcess";
    private static final String TESTINPROCESS_VIEW = "testInProcess";
    private static final String TESTFINAL_VIEW = "testFinal";

    private static final String TESTID_ATRIBUTE = "testId";
    private static final String PAGEID_ATRIBUTE = "pageId";
    private static final String ANSERID_ATRIBUTE = "anserId";
    private static final String ANSER_ATRIBUTE = "anser";
    private static final String QUESTION_ATRIBUTE = "questionId";

    public static final String RIGHT_ATRIBUTE = "right";
    public static final String WRONG_ATRIBUTE = "wrong";

    private final QuestionService questionService;
    private final UserService userService;

    public TestInProcess(QuestionService questionService, UserService userService) {
        this.questionService = questionService;
        this.userService = userService;
    }

    @ModelAttribute("right")
    public int createRight() {
        return 0;
    }

    @ModelAttribute("wrong")
    public int createWrong() {
        return 0;
    }

    @GetMapping
    public String getMethod(
            @RequestParam(name = TESTID_ATRIBUTE, defaultValue = "0") Long testId,
            @RequestParam(name = PAGEID_ATRIBUTE, defaultValue = "0") int pageId,
            Model model,
            @AuthenticationPrincipal UserPrincipal principal,
            SessionStatus sessionStatus) {
        var questionPage = questionService.getAll(testId, pageId, 1);
        if (pageId == 0)
            sessionStatus.setComplete();
        if (questionPage.getContent().isEmpty()) {
            int right = (int) model.getAttribute(RIGHT_ATRIBUTE);
            int wrong = (int) model.getAttribute(WRONG_ATRIBUTE);
            int sum = right + wrong;
            if (sum == 0)
                sum = 1;
            int score = 10 * right / sum;
            userService.addUserTests(principal.getId(), testId, score);
            model.addAttribute("score", score);
            model.addAttribute(TESTID_ATRIBUTE, testId);
            sessionStatus.setComplete();
            return TESTFINAL_VIEW;
        }
        var question = questionPage.getContent().get(0);
        model.addAttribute("question", question);
        model.addAttribute(TESTID_ATRIBUTE, testId);
        model.addAttribute(PAGEID_ATRIBUTE, pageId);
        model.addAttribute(QUESTION_ATRIBUTE, question.getId());
        var variants = new ArrayList<AnserElementDto>();
        variants.add(new AnserElementDto(false, question.getVariant1(), 1));
        variants.add(new AnserElementDto(false, question.getVariant2(), 2));
        variants.add(new AnserElementDto(false, question.getVariant3(), 3));
        variants.add(new AnserElementDto(false, question.getVariant4(), 4));
        model.addAttribute(ANSER_ATRIBUTE, new AnserDto(variants, 0));
        return TESTINPROCESS_VIEW;
    }

    @PostMapping("/ans/")
    public String postMethod(
            @ModelAttribute(name = ANSER_ATRIBUTE) AnserDto anser,
            @RequestParam(name = TESTID_ATRIBUTE, defaultValue = "0") Long testId,
            @RequestParam(name = PAGEID_ATRIBUTE, defaultValue = "0") int pageId,
            @RequestParam(name = QUESTION_ATRIBUTE, defaultValue = "0") Long questionId,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserPrincipal principal,
            Model model) {
        int anserId = anser.getValue();
        if (questionService.isAnserRight(questionId, anserId)) {
            model.addAttribute(RIGHT_ATRIBUTE, (int) model.getAttribute(RIGHT_ATRIBUTE) + 1);
        } else {
            model.addAttribute(WRONG_ATRIBUTE, (int) model.getAttribute(WRONG_ATRIBUTE) + 1);
        }
        redirectAttributes.addAttribute(TESTID_ATRIBUTE, testId);
        redirectAttributes.addAttribute(PAGEID_ATRIBUTE, pageId + 1);
        return Constants.REDIRECT_VIEW + URL;
    }

}
