package org.example.project3.db;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.project3.model.Movie;
import java.util.List;
import java.util.Optional;

/** This class serves as a repository for managing movie entities.
 It provides methods to perform CRUD operations on the database.**/
@ApplicationScoped // Indicates that the class is a CDI (Contexts and Dependency Injection) bean with a single instance for the application lifecycle.
@Transactional // Ensures that methods in this class are executed within a database transaction.
public class MovieRepository {
    @PersistenceContext // Injects the EntityManager for interacting with the persistence context (database).
    private EntityManager entityManager; // The EntityManager is used for database operations like querying, persisting, and updating entities.


    /**
     * @param movie Method to create a new movie in the database
     */
    public void create(Movie movie) {
        entityManager.persist(movie); // The persist operation adds the movie to the persistence context and commits it to the database.
    }

    /**
     * @param id Method to find a movie by its ID
     * @return
     */
    public Movie findById(Long id) {
        return entityManager.find(Movie.class, id); // Returns the movie if found, otherwise returns null.
    }

    /**
     * @return Method to retrieve all movies from the database
     */
    public List<Movie> findAll() {
        return entityManager.createQuery("SELECT m FROM Movie m", Movie.class) // The query fetches all rows from the Movie table.
                .getResultList(); // Executes the query and returns the results as a list of Movie objects.
    }

    /**
     * @param movie This method update movies information
     */
    public void update(Movie movie) {
        entityManager.merge(movie);
    }

    /**
     * @param id This method delete movie from the list
     */
    public void delete(Long id) {
        Movie movie = findById(id);//Fetch the movie
        if (movie != null) {
            entityManager.remove(movie);//Remove it if found
        }
    }
}

