package com.app.controllers;

import com.app.service.CategoryService;
import com.app.service.JSONService;
import com.app.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final LevelService levelService;
    private final CategoryService categoryService;
    private final JSONService jsonService;

    @GetMapping("/")
    public String test() {
        categoryService.addStandardCategory();
        levelService.addAllLevel();
        jsonService.saveQuestions();
        return "homepage";
    }
}
