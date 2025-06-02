package org.example.movie.repository;

/**
 * DAO class for managing Customer entity operations using Hibernate.
 * Extends the generic DAO to provide specific data access functionality for Customer.
 */

import org.example.movie.entity.Customer;
import org.hibernate.SessionFactory;

public class CustomerDAO extends GenericDAO<Customer, Integer> {

    public CustomerDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Customer.class);
    }

}
