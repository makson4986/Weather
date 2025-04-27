package ru.weather.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import ru.weather.exceptions.DataBaseException;

import java.util.Optional;

@Repository
public abstract class BaseRepository<I, E> {
    @PersistenceContext
    protected EntityManager entityManager;
    protected  final TransactionTemplate transactionTemplate;
    protected final Class<E> clazz;

    public BaseRepository(TransactionTemplate transactionTemplate, Class<E> clazz) {
        this.transactionTemplate = transactionTemplate;
        this.clazz = clazz;
    }

    public E save(E entity) {
        try {
            return transactionTemplate.execute(status -> {
                entityManager.persist(entity);
                return entity;
            });
        } catch (RuntimeException e) {
            throw new DataBaseException(e);
        }
    }

    public void delete(E entity) {
        try {
            transactionTemplate.execute(status -> {
                entityManager.remove(entity);
                return null;
            });
        } catch (Exception e) {
            throw new DataBaseException(e);
        }
    }

    public void update(E entity) {
        try {
            transactionTemplate.execute(status -> {
                entityManager.merge(entity);
                return null;
            });
        } catch (Exception e) {
            throw new DataBaseException(e);
        }
    }

    public Optional<E> findById(I id) {
        try {
            return Optional.ofNullable(entityManager.find(clazz, id));
        } catch (Exception e) {
            throw new DataBaseException(e);
        }
    }
}
