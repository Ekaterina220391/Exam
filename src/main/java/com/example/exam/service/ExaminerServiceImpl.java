package com.example.exam.service;
import com.example.exam.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.*;

@Slf4j
public class ExaminerServiceImpl implements ExaminerService {
    private final QuestionService questionService;

    public ExaminerServiceImpl(@Qualifier("javaQuestionService") QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {

        log.info("Was invoked method for get {} random questions", amount);

        List<Question> allQuestions = new ArrayList<>(questionService.getAll());


        log.debug("Total questions available in storage: {}", allQuestions.size());

        if (amount > allQuestions.size()) {

            log.warn("Requested amount ({}) exceeds total questions count ({})", amount, allQuestions.size());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недостаточно вопросов!");
        }

        Set<Question> result = new HashSet<>();
        Random random = new Random();
        while (result.size() < amount) {
            int index = random.nextInt(allQuestions.size());
            result.add(allQuestions.get(index));
        }


        log.info("Successfully picked {} random questions", result.size());
        return result;
    }
}

