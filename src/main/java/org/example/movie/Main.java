package org.example.movie;

/**
 * The entry point of the application.
 * Initializes all DAOs and services, simulates real-world operations:
 * - Customer registration
 * - Releasing a film
 * - Renting and returning a film
 */

import org.example.movie.config.HibernateUtil;
import org.example.movie.dto.CustomerDTO;
import org.example.movie.dto.FilmDTO;
import org.example.movie.dto.RentalDTO;
import org.example.movie.entity.Customer;
import org.example.movie.repository.*;
import org.example.movie.service.CustomerService;
import org.example.movie.service.FilmService;
import org.example.movie.service.RentalService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.math.BigDecimal;
import java.util.Set;

public class Main {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        sessionFactory = HibernateUtil.getSessionFactory();

        CustomerDAO customerDAO = new CustomerDAO(sessionFactory);
        FilmDAO filmDAO = new FilmDAO(sessionFactory);
        InventoryDAO inventoryDAO = new InventoryDAO(sessionFactory);
        RentalDAO rentalDAO = new RentalDAO(sessionFactory);
        PaymentDAO paymentDAO = new PaymentDAO(sessionFactory);
        StaffDAO staffDAO = new StaffDAO(sessionFactory);
        AddressDAO addressDAO = new AddressDAO(sessionFactory);
        CityDAO cityDAO = new CityDAO(sessionFactory);
        CountryDAO countryDAO = new CountryDAO(sessionFactory);
        StoreDAO storeDAO = new StoreDAO(sessionFactory);
        LanguageDAO languageDAO = new LanguageDAO(sessionFactory);
        ActorDAO actorDAO = new ActorDAO(sessionFactory);
        CategoryDAO categoryDAO = new CategoryDAO(sessionFactory);

        CustomerService customerService = new CustomerService(customerDAO, addressDAO, cityDAO, countryDAO, storeDAO);
        FilmService filmService = new FilmService(filmDAO, inventoryDAO, languageDAO, actorDAO, categoryDAO, storeDAO);
        RentalService rentalService = new RentalService(rentalDAO, paymentDAO, inventoryDAO, staffDAO, customerDAO);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCountryId((short) 20);
        customerDTO.setCityId((short) 1);
        customerDTO.setStoreId((short) 1);
        customerDTO.setAddress("Test Address, 123");
        customerDTO.setDistrict("Test District");
        customerDTO.setPostalCode("12345");
        customerDTO.setPhone("1234567890");
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");
        customerDTO.setEmail("john.doe@example.com");

        Customer newCustomer = customerService.createCustomer(customerDTO);

        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setTitle("Test Film");
        filmDTO.setDescription("This is a test film description.");
        filmDTO.setReleaseYear(2025);
        filmDTO.setLanguageId((short) 1);
        filmDTO.setRentalDuration((short) 7);
        filmDTO.setRentalRate(BigDecimal.valueOf(4.99));
        filmDTO.setReplacementCost(BigDecimal.valueOf(19.99));
        filmDTO.setActorIds(Set.of((short) 1));
        filmDTO.setCategoryIds(Set.of((short) 1));
        filmDTO.setStoreId((short) 1);

        filmService.releaseNewFilm(filmDTO);

        Integer latestInventoryId = getLastInventoryId();

        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setCustomerId(newCustomer.getId());
        rentalDTO.setInventoryId(latestInventoryId);
        rentalDTO.setStaffId(1);
        rentalDTO.setAmount(BigDecimal.valueOf(5.99));

        rentalService.rentInventory(rentalDTO);

        Integer latestRentalId = getLastRentalId();
        rentalService.returnFilm(latestRentalId);

        HibernateUtil.shutdown();
    }

    private static Integer getLastInventoryId() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select max(i.id) from Inventory i", Integer.class).getSingleResult();
        }
    }

    private static Integer getLastRentalId() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select max(r.id) from Rental r", Integer.class).getSingleResult();
        }
    }
}
