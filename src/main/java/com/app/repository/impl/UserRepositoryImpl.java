package com.app.repository.impl;

import com.app.model.User;
import com.app.repository.UserRepository;
import com.app.repository.generic.AbstractGenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends AbstractGenericDao<User> implements UserRepository {
}
