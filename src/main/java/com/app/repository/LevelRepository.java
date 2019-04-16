package com.app.repository;

import com.app.model.Level;
import com.app.model.LevelPoint;
import com.app.repository.generic.GenericDao;

import java.util.Optional;

public interface LevelRepository extends GenericDao<Level> {
    Optional<Level> findByName(LevelPoint name);
}
