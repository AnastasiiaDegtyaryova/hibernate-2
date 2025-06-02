package org.example.movie.service;

/**
 * Service class responsible for handling film rental and return operations.
 * Coordinates customer, inventory, staff, rental, and payment entities.
 * Ensures that rentals and payments are executed as atomic transactions.
 */

import org.example.movie.config.HibernateUtil;
import org.example.movie.dto.RentalDTO;
import org.example.movie.entity.*;
import org.example.movie.repository.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDateTime;
import java.util.List;

public class RentalService {

    private final RentalDAO rentalDAO;
    private final PaymentDAO paymentDAO;
    private final InventoryDAO inventoryDAO;
    private final StaffDAO staffDAO;
    private final CustomerDAO customerDAO;

    public RentalService(RentalDAO rentalDAO, PaymentDAO paymentDAO,
                         InventoryDAO inventoryDAO, StaffDAO staffDAO,
                         CustomerDAO customerDAO) {
        this.rentalDAO = rentalDAO;
        this.paymentDAO = paymentDAO;
        this.inventoryDAO = inventoryDAO;
        this.staffDAO = staffDAO;
        this.customerDAO = customerDAO;
    }

    public void returnFilm(Integer rentalId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Rental rental = rentalDAO.findById(session, rentalId);
            if (rental != null) {
                rental.setReturnDate(LocalDateTime.now());
                rental.setLastUpdate(LocalDateTime.now());
                rentalDAO.update(session, rental);
            }

            tx.commit();
            System.out.println("Rental return registered successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rentInventory(RentalDTO dto) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            String hql = "select r from Rental r where r.inventory.id = :inventoryId order by r.rentalDate desc";
            List<Rental> rentals = session.createQuery(hql, Rental.class)
                    .setParameter("inventoryId", dto.getInventoryId())
                    .setMaxResults(1)
                    .getResultList();

            if (!rentals.isEmpty() && rentals.get(0).getReturnDate() == null) {
                throw new IllegalStateException("Inventory is not available for rental.");
            }

            Customer customer = customerDAO.findById(session, dto.getCustomerId());
            Inventory inventory = inventoryDAO.findById(session, dto.getInventoryId());
            Staff staff = staffDAO.findById(session, dto.getStaffId());

            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setInventory(inventory);
            rental.setRentalDate(LocalDateTime.now());
            rental.setLastUpdate(LocalDateTime.now());
            rental.setStaff(staff);

            rentalDAO.save(session, rental);

            Payment payment = new Payment();
            payment.setCustomer(customer);
            payment.setRental(rental);
            payment.setStaff(staff);
            payment.setAmount(dto.getAmount());
            payment.setPaymentDate(LocalDateTime.now());

            paymentDAO.save(session, payment);

            tx.commit();
            System.out.println("Rental and payment registered successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
