package org.example.movie.repository;

import org.example.movie.entity.Payment;
import org.hibernate.SessionFactory;

public class PaymentDAO extends GenericDAO<Payment, Short> {

    public PaymentDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Payment.class);
    }
}
