package org.example.movie.repository;

/**
 * A generic Data Access Object (DAO) class that provides basic CRUD operations.
 * It is extended by specific DAO classes for each entity.
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.io.Serializable;
import java.util.List;

public class GenericDAO<T, ID extends Serializable> {

    protected final SessionFactory sessionFactory;
    private final Class<T> entityClass;

    public GenericDAO(SessionFactory sessionFactory, Class<T> entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    public T findById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(entityClass, id);
        }
    }

    public List<T> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list();
        }
    }

    public void save(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        }
    }

    public void update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
        }
    }

    public void delete(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(entity);
            tx.commit();
        }
    }

    public T findById(Session session, ID id) {
        return session.get(entityClass, id);
    }

    public void save(Session session, T entity) {
        session.persist(entity);
    }

    public void update(Session session, T entity) {
        session.merge(entity);
    }

    public void delete(Session session, T entity) {
        session.remove(entity);
    }
}
