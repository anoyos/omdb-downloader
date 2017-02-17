package net.sprava.omdb.service;

import java.util.List;
import net.sprava.omdb.model.Movie;

/**
 * 
 * @author Nikolay Koretskyy
 *
 */
public interface OmdbRestClient {

	public Movie getMovie(String imdbId);

	public List<Movie> getFirstPageMoviesByTitleAndYear(String title, String year);
	
	public List<Movie> getAllMoviesByTitleAndYear(String title, String year);

}
