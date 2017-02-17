package net.sprava.omdb.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import net.sprava.omdb.model.Movie;

/**
 * 
 * @author Nikolay Koretskyy
 *
 */
@Service
public class OmdbRestClientImpl implements OmdbRestClient {

	private static final String REST_SERVICE_URI = "http://www.omdbapi.com/";

	@Override
	public Movie getMovie(String imdbId) {
		RestTemplate restTemplate = new RestTemplate();
		Movie movie = restTemplate.getForObject(REST_SERVICE_URI + "?i=" + imdbId + "&plot=short&r=json", Movie.class);
		return movie;
	}

	@Override
	public List<Movie> getFirstPageMoviesByTitleAndYear(String title, String year) {

		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		JsonNode moviesJson = null;
		List<Movie> movies = null;

		ResponseEntity<String> response = restTemplate.getForEntity(
				REST_SERVICE_URI + "?s=" + title + "&y=" + year + "&plot=short&r=json&type=movie", String.class);

		try {
			JsonNode root = mapper.readTree(response.getBody());
			if (root.has("Search")) {
				moviesJson = root.path("Search");
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (Optional.ofNullable(moviesJson).isPresent()) {
			try {
				movies = mapper.readValue(moviesJson.toString(), new TypeReference<List<Movie>>() {
				});
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/*
		 * movies.stream()
		 * 		.map(m -> m.getTitle())
		 * 		.forEach(System.out::println);
		 */
		return movies;
	}

	@Override
	public List<Movie> getAllMoviesByTitleAndYear(String title, String year) {

		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		String totalResults = null;
		JsonNode moviesJson = null;
		List<Movie> pageMovies = null;
		List<Movie> movies = null;

		ResponseEntity<String> response = restTemplate.getForEntity(
				REST_SERVICE_URI + "?s=" + title + "&y=" + year + "&plot=short&r=json&type=movie", String.class);

		try {
			JsonNode root = mapper.readTree(response.getBody());
			if (root.has("totalResults")) {
				totalResults = root.get("totalResults").asText();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (Optional.ofNullable(totalResults).isPresent() && !totalResults.equals("0")) {

			for (int page = 1; page <= (Integer.parseInt(totalResults) / 10); page++) {

				response = restTemplate.getForEntity(
						REST_SERVICE_URI + "?s=" + title + "&y=" + year + "&page=" + page + "&plot=short&r=json&type=movie", String.class);

				try {
					JsonNode root = mapper.readTree(response.getBody());
					if (root.has("Search")) {
						moviesJson = root.path("Search");
					}
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (Optional.ofNullable(moviesJson).isPresent()) {
					try {
						pageMovies = mapper.readValue(moviesJson.toString(), new TypeReference<List<Movie>>() {
						});
					} catch (JsonParseException e) {
						e.printStackTrace();
					} catch (JsonMappingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (Optional.ofNullable(movies).isPresent()) {
					movies.addAll(pageMovies);
				} else {
					movies = pageMovies;
				}
			}
		}

		/*
		 * movies.stream()
		 * 		.map(m -> m.getTitle())
		 * 		.forEach(System.out::println);
		 */
		return movies;
	}

}
