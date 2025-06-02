package org.example.movie.repository;

import org.example.movie.entity.Rental;
import org.hibernate.SessionFactory;

public class RentalDAO extends GenericDAO<Rental, Integer> {

    public RentalDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Rental.class);
    }

}
