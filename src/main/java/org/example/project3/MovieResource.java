package org.example.project3;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.example.project3.db.MovieRepository;
import org.example.project3.model.Movie;
import java.util.List;


/**This class provides RESTful tools for movie management.**/
@Path("/movies")// This annotation defines the base URL path for the resource.
public class MovieResource {
    @Inject // Injecting the MovieRepository to interact with the database.
    MovieRepository movieRepository; // Injecting the MovieRepository to interact with the database.

    /**
     * @return Method to handle GET requests to retrieve all movies
     */
    @GET
    public Response getMovies() {
        // Calling the repository to fetch all movies
        List<Movie> movies = movieRepository.findAll();
        // Returning an HTTP 200 OK response with the list of movies
        return Response.ok().entity(movies).build();
    }

    /**
     * @param movie Method to handle POST requests to create a new movie
     * @return
     */
    @POST
    public Response createMovie(Movie movie) {
        // The movie entity is passed as a parameter in the request body
        // Persisting the new movie using the repository
        movieRepository.create(movie);
        // Returning an HTTP 201 CREATED response with the newly created movie
        return Response.status(Response.Status.CREATED).entity(movie).build();
    }

    /**
     * @param id Method to handle GET requests to retrieve a specific movie by its ID
     * @return
     */
    @GET
    @Path("/{id}") // This annotation indicates that the method will handle requests with a specific movie ID.
    public Response getMovie(@PathParam("id") Long id) { // @PathParam binds the value from the URL to the method parameter.
        // Fetching the movie from the repository by ID
        Movie movie = movieRepository.findById(id);

        if (movie != null) {
            return Response.ok(movie).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * @param id
     * @param updatedMovie Method to handle PUT requests to update an existing movie by its ID
     * @return
     */
    @PUT
    @Path("/{id}")
    public Response updateMovie(@PathParam("id") Long id, Movie updatedMovie) {
        // Get movie from repository by using id
        Movie existingMovie = movieRepository.findById(id);

        /* If movie was found, update it */
        if (existingMovie != null) {
            existingMovie.setTitle(updatedMovie.getTitle());
            existingMovie.setGenre(updatedMovie.getGenre());
            existingMovie.setReleaseYear(updatedMovie.getReleaseYear());
            existingMovie.setDescription(updatedMovie.getDescription());
            existingMovie.setDirector(updatedMovie.getDirector());

            // Saving updated movie in repository
            movieRepository.update(existingMovie);

            // Return updated movie
            return Response.ok(existingMovie).build();
        } else {
            // If movie was not found, return 404
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * @param id Method to handle DELETE requests to remove a movie by its ID
     * @return
     */
    @DELETE
    @Path("/{id}")
    public Response deleteMovie(@PathParam("id") Long id) {
        // Fetching the movie from the repository by ID
        Movie movie = movieRepository.findById(id);
        if (movie != null) {
            movieRepository.delete(id);
            return Response.ok().entity("Movie deleted").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}


