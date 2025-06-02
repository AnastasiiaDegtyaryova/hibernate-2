package org.example.movie.repository;

import org.example.movie.entity.City;
import org.hibernate.SessionFactory;

public class CityDAO extends GenericDAO<City, Short>{
    public CityDAO(SessionFactory sessionFactory) {
        super(sessionFactory, City.class);
    }

}
