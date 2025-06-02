package org.example.movie.repository;

import org.example.movie.entity.Staff;

public class StaffDAO extends GenericDAO<Staff, Integer> {

    public StaffDAO(org.hibernate.SessionFactory sessionFactory) {
        super(sessionFactory, Staff.class);
    }

}
