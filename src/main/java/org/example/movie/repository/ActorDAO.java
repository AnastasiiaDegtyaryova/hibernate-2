package org.example.movie.repository;

import org.example.movie.entity.Actor;
import org.hibernate.SessionFactory;

public class ActorDAO extends GenericDAO<Actor, Short> {

    public ActorDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Actor.class);
    }
}
