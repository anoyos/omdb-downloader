package net.sprava.omdb.service.batch;

import java.util.Date;
import java.util.Optional;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import net.sprava.omdb.model.Movie;
import net.sprava.omdb.persistence.MovieRepository;
import net.sprava.omdb.service.OmdbRestClient;

public class MovieProcessor implements ItemProcessor<Movie, Movie> {

	@Autowired
	OmdbRestClient omnbRestClient;

	@Autowired
	MovieRepository movieRepository;

	@Override
	public Movie process(Movie movie) throws Exception {

		String imdbId = movie.getImdbId();
		movie = omnbRestClient.getMovie(imdbId);

		Movie alreadyDownloadedMovie = movieRepository.findByImdbId(movie.getImdbId());
		if (Optional.ofNullable(alreadyDownloadedMovie).isPresent()) {
			movie.setCreatedAt(alreadyDownloadedMovie.getCreatedAt());
			movie.setModifiedAt(new Date());
		} else {
			movie.setCreatedAt(new Date());
		}
		return movie;
	}

}
