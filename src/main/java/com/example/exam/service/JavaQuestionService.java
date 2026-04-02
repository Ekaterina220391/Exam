package com.example.exam.service;

import com.example.exam.model.Question;
import java.util.*;

public class JavaQuestionService implements QuestionService {
    private final Set<Question> questions = new HashSet<>();

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question newQuestion) {
        questions.add(newQuestion);
        return newQuestion;
    }

    @Override
    public boolean remove(Question existingQuestion) {
        return questions.remove(existingQuestion);
    }

    @Override
    public Collection<Question> getAll() {
        return new ArrayList<>(questions);  // Копируем в List
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            return null;  // Пусто = null
        }
        int index = new Random().nextInt(questions.size());
        return (Question) questions.toArray()[index];  // Случайный!
    }
}
