package org.example.movie.repository;

import org.example.movie.entity.Language;
import org.hibernate.SessionFactory;

public class LanguageDAO extends GenericDAO<Language, Short> {

    public LanguageDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Language.class);
    }
}
