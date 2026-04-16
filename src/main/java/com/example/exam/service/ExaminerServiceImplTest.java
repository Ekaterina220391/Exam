package com.example.exam.service;

import com.example.exam.model.Question;
import org.springframework.web.server.ResponseStatusException;
import org.testng.annotations.Test;
import java.util.*;
import static org.testng.Assert.*;
import java.util.Arrays;


class ExaminerServiceImplTest {

    @Test
    void getQuestionsReturnsRequestedAmount() {
        // Arrange: создаём фейковый сервис с 3 вопросами
        QuestionService mockService = new QuestionService() {
            @Override
            public Question add(String question, String answer) { return null; }
            @Override
            public Question add(Question newQuestion) { return null; }
            @Override
            public boolean remove(Question existingQuestion) { return false; }
            @Override
            public Collection<Question> getAll() {
                return Arrays.asList(
                        new Question("Q1", "A1"),
                        new Question("Q2", "A2"),
                        new Question("Q3", "A3")
                );
            }
            @Override
            public Question getRandomQuestion() { return null; }
        };

        ExaminerServiceImpl service = new ExaminerServiceImpl(mockService);
        Collection<Question> result = service.getQuestions(2);

        assertEquals(2, result.size());
    }

    @Test
    void getQuestionsThrowsWhenAmountExceedsAvailable() {
        // Arrange: сервис с 1 вопросом
        QuestionService mockService = new QuestionService() {
            @Override
            public Question add(String question, String answer) { return null; }
            @Override
            public Question add(Question newQuestion) { return null; }
            @Override
            public boolean remove(Question existingQuestion) { return false; }
            @Override
            public Collection<Question> getAll() {
                return Collections.singletonList(new Question("Q1", "A1"));
            }
            @Override
            public Question getRandomQuestion() { return null; }
        };

        ExaminerServiceImpl service = new ExaminerServiceImpl(mockService);

        try {
            service.getQuestions(2);
            fail("Ожидалось исключение!");
        } catch (ResponseStatusException e) {
            assertTrue(e.getMessage().contains("Недостаточно вопросов!"));
        }
    }
}