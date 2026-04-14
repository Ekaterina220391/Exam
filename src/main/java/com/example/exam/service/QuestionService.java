package com.example.exam.service;
import com.example.exam.model.Question;
import java.util.Collection;


public interface QuestionService {
        Question add(String question, String answer);
        Question add(Question newQuestion);
        boolean remove(Question existingQuestion);
        Collection<Question> getAll();
        Question getRandomQuestion();
    }

