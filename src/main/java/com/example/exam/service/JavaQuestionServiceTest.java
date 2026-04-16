package com.example.exam.service;
import com.example.exam.model.Question;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import static org.testng.Assert.*;

class JavaQuestionServiceTest {

    private JavaQuestionService service;

    @BeforeMethod
    void setUp() {
        service = new JavaQuestionService();
    }

    @Test
    void addByStringsAndGetAll() {
        service.add("OOP?", "Классы");
        service.add("main?", "Точка входа");

        Collection<Question> all = service.getAll();

        assertEquals(2, all.size());
        assertTrue(all.contains(new Question("OOP?", "Классы")));
        assertTrue(all.contains(new Question("main?", "Точка входа")));
    }

    @Test
    void addByObject() {
        Question q = new Question("Java?", "Язык программирования");

        Question added = service.add(q);

        assertEquals(q, added);
        assertTrue(service.getAll().contains(q));
    }

    @Test
    void removeExistingQuestion() {
        Question q = new Question("OOP?", "Классы");
        service.add(q);

        boolean removed = service.remove(q);

        assertTrue(removed);
        assertFalse(service.getAll().contains(q));
    }

    @Test
    void removeNonExistingQuestion() {
        Question q = new Question("Нет такого", "Ответ");

        boolean removed = service.remove(q);

        assertFalse(removed);
        assertTrue(service.getAll().isEmpty());
    }

    @Test
    void getRandomQuestionFromNonEmpty() {
        service.add("OOP?", "Классы");
        service.add("main?", "Точка входа");

        Question random = service.getRandomQuestion();

        assertNotNull(random);
        assertTrue(service.getAll().contains(random));
    }

    @Test
    void getRandomQuestionFromEmpty() {
        Question random = service.getRandomQuestion();

        assertNull(random);
    }
}