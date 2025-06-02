package org.example.movie.service;

/**
 * Service class responsible for creating and managing customers.
 * It orchestrates DAO calls to handle related entities like Address, City, Country, and Store.
 * Ensures that customer creation is performed in a single transaction.
 */

import org.example.movie.config.HibernateUtil;
import org.example.movie.dto.CustomerDTO;
import org.example.movie.entity.*;
import org.example.movie.repository.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDateTime;

public class CustomerService {

    private final CustomerDAO customerDAO;
    private final AddressDAO addressDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final StoreDAO storeDAO;

    public CustomerService(CustomerDAO customerDAO, AddressDAO addressDAO,
                           CityDAO cityDAO, CountryDAO countryDAO, StoreDAO storeDAO) {
        this.customerDAO = customerDAO;
        this.addressDAO = addressDAO;
        this.cityDAO = cityDAO;
        this.countryDAO = countryDAO;
        this.storeDAO = storeDAO;
    }

    public Customer createCustomer(CustomerDTO dto) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Country country = countryDAO.findById(session, dto.getCountryId());
            City city = cityDAO.findById(session, dto.getCityId());
            Store store = storeDAO.findById(session, dto.getStoreId());

            Address address = new Address();
            address.setAddress(dto.getAddress());
            address.setCity(city);
            address.setDistrict(dto.getDistrict());
            address.setPostalCode(dto.getPostalCode());
            address.setPhone(dto.getPhone());
            address.setLastUpdate(LocalDateTime.now());
            addressDAO.save(session, address);

            Customer customer = new Customer();
            customer.setFirstName(dto.getFirstName());
            customer.setLastName(dto.getLastName());
            customer.setEmail(dto.getEmail());
            customer.setAddress(address);
            customer.setStore(store);
            customer.setActive(true);
            customer.setCreateDate(LocalDateTime.now());
            customer.setLastUpdate(LocalDateTime.now());
            customerDAO.save(session, customer);

            tx.commit();
            System.out.println("Customer created successfully!");
            return customer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
