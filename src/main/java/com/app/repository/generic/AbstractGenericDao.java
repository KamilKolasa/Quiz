package com.app.repository.generic;

import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class AbstractGenericDao<T> implements GenericDao<T> {

    @PersistenceContext
    private EntityManager entityManager;
    private Class<T> entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public T add(T t) {
        try {
            if (entityManager != null && t != null) {
                return entityManager.merge(t);
            }
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "AbstractGenericDao ADD EXCEPTION");
        }
        return null;
    }

    @Override
    public void update(T t) {
        try {
            if (entityManager != null && t != null) {
                entityManager.merge(t);
            }
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "AbstractGenericDao UPDATE EXCEPTION");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (entityManager != null && id != null) {
                T t = entityManager.find(entityClass, id);
                if (t != null) {
                    entityManager.remove(t);
                }
            }
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "AbstractGenericDao DELETE EXCEPTION");
        }
    }

    @Override
    public Optional<T> findById(Long id) {
        try {
            Optional<T> element = Optional.empty();

            if (entityManager != null && id != null) {
                element = Optional.of(entityManager.find(entityClass, id));
            }

            return element;
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "AbstractGenericDao FINDbyID EXCEPTION");
        }
    }

    @Override
    public List<T> findAll() {
        try {
            List<T> elements = null;
            if (entityManager != null) {
                Query query = entityManager.createQuery("select e from " + entityClass.getCanonicalName() + " e");
                elements = query.getResultList();
            }
            return elements;
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "AbstractGenericDao FINDall EXCEPTION");
        }
    }
}
