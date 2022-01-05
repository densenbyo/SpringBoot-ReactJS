package cz.cvut.fel.ear.lingo.dao;

import java.util.Collection;
import java.util.List;

public interface GenericDao<T> {
    T find(Long id);

    List<T> findAll();

    void persist(T entity);

    void persist(Collection<T> entitites);

    T update(T entity);

    void remove(T entity);

    boolean exist(T entity);
}
