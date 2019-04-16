package com.app.repository.impl;

import com.app.model.Level;
import com.app.model.LevelPoint;
import com.app.repository.LevelRepository;
import com.app.repository.generic.AbstractGenericDao;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LevelRepositoryImpl extends AbstractGenericDao<Level> implements LevelRepository {

    @Override
    public Optional<Level> findByName(LevelPoint name) {
        return getEntityManager()
                .createQuery("select l from Level l where l.nameLevel = :name", Level.class)
                .setParameter("name", name)
                .getResultList().stream().findFirst();
    }
}