package org.example.movie.repository;

import org.example.movie.entity.Inventory;

public class InventoryDAO extends GenericDAO<Inventory, Integer> {

    public InventoryDAO(org.hibernate.SessionFactory sessionFactory) {
        super(sessionFactory, Inventory.class);
    }
}
