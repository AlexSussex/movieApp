package com.qa.repository;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;

import com.qa.persistence.domain.Movie;
import com.qa.util.JSONUtil;


public class MovieDBRepository implements IMovieRepository {

	private static final Logger LOGGER = Logger.getLogger(MovieDBRepository.class);

	@PersistenceContext(unitName = "primary")
	private EntityManager manager;

	@Inject
	private JSONUtil util;

	@Override
	public String getAllMovies() {
		LOGGER.info("MovieDBRepository getAllMovies");
		Query query = manager.createQuery("SELECT m from Movie m");
		Collection<Movie> movies = (Collection<Movie>) query.getResultList();
		return util.getJSONForObject(movies);
	}

	@Override
	public String getAMovie(Long id) {
		Movie aMovie = getMovie(id);
		if (aMovie != null) {
			return util.getJSONForObject(aMovie);
		}
		else {
			return ("{\"message\":\"movie not found\"}");
		}
	}

	private Movie getMovie(Long id) {
		return manager.find(Movie.class, id);
	}

	@Override
	@Transactional(Transactional.TxType.REQUIRED)
	public String createMovie(String movieJSON) {
		Movie aMovie = util.getObjectForJSON(movieJSON, Movie.class);
		manager.persist(aMovie);
		return "{\"message\":\"The movie has been added to the database.\"}";
	}

	@Override
	@Transactional(Transactional.TxType.REQUIRED)
	public String deleteMovie(Long id) {
		Movie movieToSearch = findMovie(id);
		if (movieToSearch !=null) {
			manager.remove(movieToSearch);
			return "{\"message\":\"The movie has been deleted from the database.\"}";
		}
		else {
			return "{\"message\":\"The movie entered does not exist in the database.\"}";
		}
	}
	
	public Movie findMovie(Long id) {
		return manager.find(Movie.class, id);
	}

	
}
