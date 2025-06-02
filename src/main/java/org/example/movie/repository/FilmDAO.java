package org.example.movie.repository;

import org.example.movie.entity.Film;
import org.hibernate.SessionFactory;

public class FilmDAO extends GenericDAO<Film, Integer> {

    public FilmDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Film.class);
    }
}
