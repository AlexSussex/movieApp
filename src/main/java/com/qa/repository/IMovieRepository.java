package com.qa.repository;

public interface IMovieRepository {

	String getAllMovies();
	
	String getAMovie(Long id);
	
	String createMovie(String movieJSON);
	
	String deleteMovie(Long id);
	
	String updateMovie(String movieJSON);
}
