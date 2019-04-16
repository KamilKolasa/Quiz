package com.app.repository.impl;

import com.app.model.Quiz;
import com.app.repository.QuizRepository;
import com.app.repository.generic.AbstractGenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class QuizRepositoryImpl extends AbstractGenericDao<Quiz> implements QuizRepository {
}
