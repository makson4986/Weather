package ru.weather.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.weather.exceptions.DataBaseException;

import java.util.Optional;

@Repository
public abstract class BaseRepository<I, E> {
    @PersistenceContext
    protected EntityManager entityManager;
    protected final Class<E> clazz;

    public BaseRepository(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Transactional
    public E save(E entity) {
        try {
            entityManager.persist(entity);
            return entity;
        } catch (HibernateException e) {
            throw new DataBaseException(e);
        }
    }

    @Transactional
    public void delete(E entity) {
        try {
            entityManager.remove(entity);
        } catch (Exception e) {
            throw new DataBaseException(e);
        }
    }

    @Transactional
    public void update(E entity) {
        try {
            entityManager.merge(entity);
        } catch (Exception e) {
            throw new DataBaseException(e);
        }
    }

    @Transactional
    public Optional<E> findById(I id) {
        try {
            return Optional.ofNullable(entityManager.find(clazz, id));
        } catch (Exception e) {
            throw new DataBaseException(e);
        }
    }
}
