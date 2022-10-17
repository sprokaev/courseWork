package com.kaypro.coursework.service;

import com.kaypro.coursework.model.Question;

import javax.management.Query;
import java.util.Collection;

public interface ExaminerService {

    Collection<Question> getQuestions(int amount);
}
