package net.sprava.omdb.service.batch;

import java.util.List;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
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
// @Scope("step")
public class OmdbRestMovieReader implements ItemReader<Movie> {

	@Autowired
	OmdbRestClient omnbRestClient;

	// @Value("#{jobParameters['keyword']}")
	// private String keyword;

	private int nextMovieIndex = 0;
	private List<Movie> movies;

	@BeforeStep
	public void getAllMovies(final StepExecution stepExecution) {
		JobParameters parameters = stepExecution.getJobExecution().getJobParameters();
		movies = omnbRestClient.getMoviesByKeyword(parameters.getString("keyword"));
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
