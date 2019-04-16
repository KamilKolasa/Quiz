package com.app.repository;

import com.app.model.Category;
import com.app.repository.generic.GenericDao;

import java.util.Optional;

public interface CategoryRepository extends GenericDao<Category> {
    Optional<Category> findByName(String name);
}
