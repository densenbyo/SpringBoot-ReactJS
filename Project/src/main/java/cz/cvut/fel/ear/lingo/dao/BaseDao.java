package cz.cvut.fel.ear.lingo.dao;

import cz.cvut.fel.ear.lingo.model.abstracts.AbstractClass;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class BaseDao<T extends AbstractClass> implements GenericDao<T> {

    @PersistenceContext
    protected EntityManager em;

    protected final Class<T> type;

    protected BaseDao(Class<T> type) {
        this.type = type;
    }

    @Override
    public T find(Long id) {
        Objects.requireNonNull(id);
        try {
            T result = em.find(type, id);
            if(result == null) {
                throw new PersistenceException();
            } else {
                return result;
            }
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<T> findAll() {
        try {
            List<T> resultList = em.createQuery("SELECT e FROM " + type.getSimpleName() + " e", type).getResultList();
            if(resultList.size() == 0) {
                throw new PersistenceException();
            } else {
                return resultList;
            }
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void persist(T entity) {
        Objects.requireNonNull(entity);
        try {
            em.persist(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void persist(Collection<T> entities) {
        Objects.requireNonNull(entities);
        if (entities.isEmpty()) {
            return;
        }
        try {
            entities.forEach(this::persist);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public T update(T entity) {
        Objects.requireNonNull(entity);
        try {
            return em.merge(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void remove(T entity) {
        Objects.requireNonNull(entity);
        try {
            final T toRemove = em.merge(entity);
            if (toRemove != null) {
                em.remove(toRemove);
            }
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean exist(T entity) {
        Objects.requireNonNull(entity);
        return em.find(type, entity.getId()) != null;
    }
}