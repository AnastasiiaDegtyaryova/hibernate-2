package org.example.movie.service;

/**
 * Service class responsible for creating new films and making them available for rental.
 * Manages related entities like Language, Actors, Categories, and Inventory.
 * Ensures transactional integrity when releasing a new film.
 */

import org.example.movie.config.HibernateUtil;
import org.example.movie.dto.FilmDTO;
import org.example.movie.entity.*;
import org.example.movie.repository.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;


public class FilmService {

    private final FilmDAO filmDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final ActorDAO actorDAO;
    private final CategoryDAO categoryDAO;
    private final StoreDAO storeDAO;

    public FilmService(FilmDAO filmDAO, InventoryDAO inventoryDAO,
                       LanguageDAO languageDAO, ActorDAO actorDAO,
                       CategoryDAO categoryDAO, StoreDAO storeDAO) {
        this.filmDAO = filmDAO;
        this.inventoryDAO = inventoryDAO;
        this.languageDAO = languageDAO;
        this.actorDAO = actorDAO;
        this.categoryDAO = categoryDAO;
        this.storeDAO = storeDAO;
    }

    public void releaseNewFilm(FilmDTO dto) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Language language = languageDAO.findById(session, dto.getLanguageId());
            Set<Actor> actors = dto.getActorIds().stream()
                    .map(id -> actorDAO.findById(session, id))
                    .collect(Collectors.toSet());
            Set<Category> categories = dto.getCategoryIds().stream()
                    .map(id -> categoryDAO.findById(session, id))
                    .collect(Collectors.toSet());
            Store store = storeDAO.findById(session, dto.getStoreId());

            Film film = new Film();
            film.setTitle(dto.getTitle());
            film.setDescription(dto.getDescription());
            film.setReleaseYear(dto.getReleaseYear());
            film.setLanguage(language);
            film.setRentalDuration(dto.getRentalDuration());
            film.setRentalRate(dto.getRentalRate());
            film.setReplacementCost(dto.getReplacementCost());
            film.setLastUpdate(LocalDateTime.now());
            film.setActors(actors);
            film.setCategories(categories);

            filmDAO.save(session, film);

            Inventory inventory = new Inventory();
            inventory.setFilm(film);
            inventory.setStore(store);
            inventory.setLastUpdate(LocalDateTime.now());

            inventoryDAO.save(session, inventory);

            tx.commit();
            System.out.println("Film created and inventory added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
