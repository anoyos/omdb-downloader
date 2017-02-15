package net.sprava.omdb.service;

/**
 * 
 * @author Nikolay Koretskyy
 *
 */
public interface OmnbService {

	public String downloadMovieList(String keyword);

	public String downloadMovie(String imdbId);
}
