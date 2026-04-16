package com.example.exam.service;
import com.example.exam.model.Question;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionService questionService;

    public ExaminerServiceImpl(@Qualifier("javaQuestionService") QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        List<Question> allQuestions = new ArrayList<>(questionService.getAll());

        if (amount > allQuestions.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недостаточно вопросов!");
        }

        Set<Question> result = new HashSet<>();
        Random random = new Random();
        while (result.size() < amount) {
            int index = random.nextInt(allQuestions.size());
            result.add(allQuestions.get(index));
        }
        return result;
    }
}