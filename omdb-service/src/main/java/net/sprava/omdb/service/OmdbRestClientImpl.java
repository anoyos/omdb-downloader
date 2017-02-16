package net.sprava.omdb.service;

import java.io.IOException;
import java.util.List;

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
	public List<Movie> getMoviesByKeyword(String keyword) {

		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		ResponseEntity<String> response = restTemplate.getForEntity(
				REST_SERVICE_URI + "?s=" + keyword + "&y=2016&plot=short&r=json&type=movie", String.class);

		JsonNode moviesJson = null;
		try {
			JsonNode root = mapper.readTree(response.getBody());
			moviesJson = root.path("Search");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Movie> movies = null;
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

		/*
		 * movies.stream() .map(m -> m.getTitle())
		 * .forEach(System.out::println);
		 */

		return movies;
	}

}
