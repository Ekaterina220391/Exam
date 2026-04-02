package com.example.exam;

import com.example.exam.service.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamApplication.class, args);
    }

    @Bean
    @Qualifier("javaQuestionService")
    public QuestionService javaQuestionService() {
        JavaQuestionService service = new JavaQuestionService();
        service.add("OOP?", "Классы");
        service.add("main?", "Точка входа");
        return service;
    }

    @Bean
    public ExaminerService examinerService(QuestionService javaQuestionService) {
        return new ExaminerServiceImpl(javaQuestionService);
    }

}
