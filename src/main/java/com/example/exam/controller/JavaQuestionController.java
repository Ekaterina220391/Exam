package com.example.exam.controller;

import com.example.exam.model.Question;
import com.example.exam.service.QuestionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/exam/java")
public class JavaQuestionController {

    private final QuestionService javaQuestionService;

    public JavaQuestionController(@Qualifier("javaQuestionService") QuestionService javaQuestionService) {
        this.javaQuestionService = javaQuestionService;
    }

    @GetMapping
    public Collection<Question> getAll() {
        return javaQuestionService.getAll();
    }

    @PostMapping("/add")
    public Question add(@RequestParam String question, @RequestParam String answer) {
        return javaQuestionService.add(question, answer);
    }

    @DeleteMapping("/remove")
    public boolean remove(@RequestParam String question, @RequestParam String answer) {
        return javaQuestionService.remove(new Question(question, answer));
    }
}
