package net.sprava.omdb.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sprava.omdb.model.Movie;
import net.sprava.omdb.persistence.MovieRepository;

/**
 * 
 * @author Nikolay Koretskyy
 *
 */
@Service
public class OmnbServiceImpl implements OmnbService {

	@Autowired
	OmnbRestClientImpl omnbRestClient;

	@Autowired
	MovieRepository movieRepository;

	@Override
	public String downloadMovieList(String keyword) {

		if (keyword.isEmpty()) {
			return "Nothing has been downloaded. Keyword doesn't set.";
		} else {
			List<Movie> movies = omnbRestClient.getMoviesByKeyword(keyword);

			movies.forEach(movie -> {
				String imdbId = movie.getImdbId();
				movie = omnbRestClient.getMovie(imdbId);

				Movie areadyDownloadedMovie = movieRepository.findByImdbId(imdbId);
				if (Optional.ofNullable(areadyDownloadedMovie).isPresent()) {
					movie.setCreatedAt(areadyDownloadedMovie.getCreatedAt());
					movie.setModifiedAt(new Date());
				} else {
					movie.setCreatedAt(new Date());
				}
				movieRepository.save(movie);
			});

			return "Movies have been downloaded.";
		}
	}

	@Override
	public String downloadMovie(String imdbId) {

		if (imdbId.isEmpty()) {
			return "Movie hasn't been downloaded. imdbId doesn't set.";
		} else {
			Movie movie = omnbRestClient.getMovie(imdbId);

			Movie areadyDownloadedMovie = movieRepository.findByImdbId(imdbId);
			if (Optional.ofNullable(areadyDownloadedMovie).isPresent()) {
				movie.setCreatedAt(areadyDownloadedMovie.getCreatedAt());
				movie.setModifiedAt(new Date());
			} else {
				movie.setCreatedAt(new Date());
			}
			movieRepository.save(movie);
		}

		return "Movie has been downloaded.";
	}
}
