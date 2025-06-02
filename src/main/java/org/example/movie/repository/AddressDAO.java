package org.example.movie.repository;

import org.example.movie.entity.Address;
import org.hibernate.SessionFactory;

public class AddressDAO extends GenericDAO<Address, Short> {

    public AddressDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Address.class);
    }

}
