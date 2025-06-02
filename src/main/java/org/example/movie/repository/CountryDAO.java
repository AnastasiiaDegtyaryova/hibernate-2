package org.example.movie.repository;

import org.example.movie.entity.Country;
import org.hibernate.SessionFactory;

public class CountryDAO extends GenericDAO<Country, Short> {
    public CountryDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Country.class);
    }
}
