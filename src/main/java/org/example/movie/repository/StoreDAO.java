package org.example.movie.repository;

import org.example.movie.entity.Store;

public class StoreDAO extends GenericDAO<Store, Short> {

    public StoreDAO(org.hibernate.SessionFactory sessionFactory) {
        super(sessionFactory, Store.class);
    }

}
