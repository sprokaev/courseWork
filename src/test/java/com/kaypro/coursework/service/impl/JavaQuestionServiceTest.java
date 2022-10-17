package com.kaypro.coursework.service.impl;

import com.kaypro.coursework.exception.QuestionAlreadyExistException;
import com.kaypro.coursework.exception.QuestionNotFoundException;
import com.kaypro.coursework.model.Question;
import com.kaypro.coursework.service.QuestionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class JavaQuestionServiceTest {

    private final QuestionService questionService = new JavaQuestionService();

    @AfterEach
    public void afterEach() {
        Collection<Question> questions = questionService.getAll();
        questions.forEach(questionService::remove);
    }

    @Test
    void addTest() {
        assertThat(questionService.getAll()).isEmpty();

        Question expected1 = new Question("Q1", "A1");
        Question expected2 = new Question("Q2", "A2");
        questionService.add(expected1);
        questionService.add(expected2.getQuestion(), expected2.getAnswer());

        assertThat(questionService.getAll()).hasSize(2);
        assertThat(questionService.getAll()).contains(expected1, expected2);
    }

    @Test
    void addNegativeTest() {
        assertThat(questionService.getAll()).isEmpty();

        Question expected = addOneQuestion();

        assertThatExceptionOfType(QuestionAlreadyExistException.class)
                .isThrownBy(() -> questionService.add(expected));

        assertThatExceptionOfType(QuestionAlreadyExistException.class)
                .isThrownBy(() -> questionService.add(expected.getQuestion(), expected.getAnswer()));
    }

    @Test
    void removeTest() {
        assertThat(questionService.getAll()).isEmpty();

        Question expected = addOneQuestion();

        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> questionService.remove(new Question("Q2", "A2")));

        questionService.remove(expected);
        assertThat(questionService.getAll()).isEmpty();
    }

    @Test
    void getRandomQuestionTest() {
        assertThat(questionService.getAll()).isEmpty();

        int size = 5;
        for (int i = 1; i <= 5; i++) {
            addOneQuestion("Q" + i, "A" + i);
        }
        assertThat(questionService.getAll()).hasSize(size);
        assertThat(questionService.getRandomQuestion()).isIn(questionService.getAll());
    }

    private Question addOneQuestion() {
        return addOneQuestion("Q1", "A1");
    }

    private Question addOneQuestion(String question, String answer) {
        int size = questionService.getAll().size();

        Question expected = new Question(question, answer);
        questionService.add(expected);

        assertThat(questionService.getAll()).hasSize(size + 1);
        assertThat(questionService.getAll()).contains(expected);

        return expected;
    }
}