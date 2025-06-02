package org.example.movie.repository;

import org.example.movie.entity.Category;
import org.hibernate.SessionFactory;

public class CategoryDAO extends GenericDAO<Category, Short> {

    public CategoryDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Category.class);
    }
}
