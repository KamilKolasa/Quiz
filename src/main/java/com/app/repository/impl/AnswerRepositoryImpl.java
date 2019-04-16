package com.app.repository.impl;

import com.app.model.Answer;
import com.app.repository.AnswerRepository;
import com.app.repository.generic.AbstractGenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerRepositoryImpl extends AbstractGenericDao<Answer> implements AnswerRepository {
}
