package net.sprava.omdb.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.sprava.omdb.model.Movie;

/**
 * 
 * @author Nikolay Koretskyy
 *
 */
@Service
public class OmnbRestClient {

	private static final String REST_SERVICE_URI = "http://www.omdbapi.com/";

	/* GET */
	public Movie getMovie() {
		System.out.println("Testing getMovie API----------");
		RestTemplate restTemplate = new RestTemplate();
		Movie movie = restTemplate.getForObject(REST_SERVICE_URI + "?t=The+Legacy+of+Arrow+Development&y=&plot=short&r=json", Movie.class);
		System.out.println(movie.getTitle());
		return movie;

	}
	
}
