package com.kaypro.coursework.service.impl;

import com.kaypro.coursework.exception.QuestionAlreadyExistException;
import com.kaypro.coursework.exception.QuestionNotFoundException;
import com.kaypro.coursework.model.Question;
import com.kaypro.coursework.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JavaQuestionService implements QuestionService {

    private final Set<Question> questions;
    private final Random random;

    public JavaQuestionService() {
        this.questions = new HashSet<>();
        this.random = new Random();
    }

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {
        if (!questions.add(question)) {
            throw new QuestionAlreadyExistException();
        };
        return question;
    }

    @Override
    public Question remove(Question question) {
        if (!questions.remove(question)) {
            throw new QuestionNotFoundException();
        };
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return new HashSet<>(questions);
    }

    @Override
    public Question getRandomQuestion() {
        return new ArrayList<>(questions).get(random.nextInt(questions.size()));
    }
}
