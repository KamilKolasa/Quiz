package com.app.repository.impl;

import com.app.model.Category;
import com.app.repository.CategoryRepository;
import com.app.repository.generic.AbstractGenericDao;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CategoryRepositoryImpl extends AbstractGenericDao<Category> implements CategoryRepository {

    @Override
    public Optional<Category> findByName(String name) {
        return getEntityManager()
                .createQuery("select c from Category c where c.name = :name", Category.class)
                .setParameter("name", name)
                .getResultList().stream().findFirst();
    }
}
