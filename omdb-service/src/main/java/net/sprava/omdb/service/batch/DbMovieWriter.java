package net.sprava.omdb.service.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import net.sprava.omdb.model.Movie;
import net.sprava.omdb.service.OmdbService;

/**
 * 
 * @author Nikolay Koretskyy
 *
 */
public class DbMovieWriter implements ItemWriter<Movie> {

	@Autowired
	OmdbService omdbService;

	@Override
	public void write(List<? extends Movie> movies) throws Exception {
		omdbService.saveMovieListToDb((List<Movie>) movies);
	}
}
