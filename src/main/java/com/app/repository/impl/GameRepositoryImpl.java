package com.app.repository.impl;

import com.app.model.Game;
import com.app.repository.GameRepository;
import com.app.repository.generic.AbstractGenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepositoryImpl extends AbstractGenericDao<Game> implements GameRepository {
}