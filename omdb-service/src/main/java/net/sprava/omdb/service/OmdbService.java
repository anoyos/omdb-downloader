package net.sprava.omdb.service;

import java.util.List;

import net.sprava.omdb.model.Movie;

/**
 * 
 * @author Nikolay Koretskyy
 *
 */
public interface OmdbService {

	public String downloadMovie(String imdbId);

	public String downloadMovieListUsual(String title, String year);

	public String downloadMovieListBatch(String title, String year);

	public void saveMovieListToDb(List<Movie> movies);
}
