package org.example.movie.repository;

import org.example.movie.entity.FilmText;

public class FilmTextDAO extends GenericDAO<FilmText, Integer> {

    public FilmTextDAO(org.hibernate.SessionFactory sessionFactory) {
        super(sessionFactory, FilmText.class);
    }
}
