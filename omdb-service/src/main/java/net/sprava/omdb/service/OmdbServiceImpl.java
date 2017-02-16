package net.sprava.omdb.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
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
public class OmdbServiceImpl implements OmdbService {

	@Autowired
	OmdbRestClient omnbRestClient;

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	private static final Logger log = Logger.getLogger(OmdbServiceImpl.class);

	@Override
	public String downloadMovie(String imdbId) {

		if (imdbId.isEmpty()) {
			return "Movie hasn't been downloaded. imdbId doesn't set.";
		} else {
			Movie movie = omnbRestClient.getMovie(imdbId);

			Movie alreadyDownloadedMovie = movieRepository.findByImdbId(imdbId);
			if (Optional.ofNullable(alreadyDownloadedMovie).isPresent()) {
				movie.setCreatedAt(alreadyDownloadedMovie.getCreatedAt());
				movie.setModifiedAt(new Date());
			} else {
				movie.setCreatedAt(new Date());
			}
			movieRepository.save(movie);
		}

		return "Movie has been downloaded.";
	}

	@Override
	public String downloadMovieListUsual(String title, String year) {

		if (title.isEmpty()) {
			return "Nothing has been downloaded. Title doesn't set.";
		} else {
			List<Movie> movies = omnbRestClient.getMoviesByTitleAndYear(title, year);

			movies.forEach(movie -> {
				String imdbId = movie.getImdbId();
				movie = omnbRestClient.getMovie(imdbId);

				Movie alreadyDownloadedMovie = movieRepository.findByImdbId(imdbId);
				if (Optional.ofNullable(alreadyDownloadedMovie).isPresent()) {
					movie.setCreatedAt(alreadyDownloadedMovie.getCreatedAt());
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
	public String downloadMovieListBatch(String title, String year) {

		if (title.isEmpty()) {
			return "Nothing has been downloaded. Title doesn't set.";
		} else {
			try {
				Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
				parameters.put("title", new JobParameter(title));
				parameters.put("year", new JobParameter(year));
				
				JobExecution jobExecution = jobLauncher.run(job, new JobParameters(parameters));
				BatchStatus status;
				do {
					status = jobExecution.getStatus();
					log.info("Status = " + status);
					Thread.sleep(1000);
				} while (status != BatchStatus.COMPLETED);
				return "Movies have been downloaded.";
			} catch (JobExecutionAlreadyRunningException e) {
				e.printStackTrace();
			} catch (JobRestartException e) {
				e.printStackTrace();
			} catch (JobInstanceAlreadyCompleteException e) {
				e.printStackTrace();
			} catch (JobParametersInvalidException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "Nothing has been downloaded. Something went wrong.";
		}
	}
	
	@Override
	public void saveMovieListToDb(List<Movie> movies) {
		movies.forEach(movieRepository::save);
	}

}
