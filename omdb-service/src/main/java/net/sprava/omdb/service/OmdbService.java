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

	public String downloadMovieListUsual(String keyword);

	public String downloadMovieListBatch(String keyword);

	public void saveMovieListToDb(List<Movie> movies);
}
