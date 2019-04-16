package com.app.repository.impl;

import com.app.model.Question;
import com.app.repository.QuestionRepository;
import com.app.repository.generic.AbstractGenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepositoryImpl extends AbstractGenericDao<Question> implements QuestionRepository {
}
