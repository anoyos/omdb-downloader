package net.sprava.omdb.service.batch;

import java.util.List;

import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import net.sprava.omdb.model.Movie;
import net.sprava.omdb.service.OmdbRestClient;

/**
 * 
 * @author Nikolay Koretskyy
 *
 */
public class OmdbRestMovieReader implements ItemReader<Movie> {

	@Autowired
	OmdbRestClient omnbRestClient;

	private int nextMovieIndex = 0;
	private List<Movie> movies;

	@BeforeStep
	public void getAllMovies() {
		movies = omnbRestClient.getMoviesByKeyword("batman");
	}

	@Override
	public Movie read() throws Exception {
		Movie nextMovie = null;
		if (nextMovieIndex < movies.size()) {
			nextMovie = movies.get(nextMovieIndex);
			nextMovieIndex++;
		}
		return nextMovie;
	}

}
