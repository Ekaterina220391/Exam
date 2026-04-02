package com.example.exam.service;


import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

class JavaQuestionServiceTest {

    @Test
    void addAndGetAll() {
        JavaQuestionService service = new JavaQuestionService();
        service.add("test?", "test");
        assertEquals(1, service.getAll().size());
    }

    @Test
    void getRandomNotNull() {
        JavaQuestionService service = new JavaQuestionService();
        service.add("OOP?", "Классы");
        assertNotNull(service.getRandomQuestion());
    }
}
