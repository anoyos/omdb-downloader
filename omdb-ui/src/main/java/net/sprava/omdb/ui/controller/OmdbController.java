package net.sprava.omdb.ui.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.sprava.omdb.model.Movie;
import net.sprava.omdb.persistence.MovieRepository;
import net.sprava.omdb.service.OmdbRestClient;
import net.sprava.omdb.service.OmdbService;

/**
 *
 * @author Nikolay Koretskyy
 *
 */
@Controller
public class OmdbController {

	@Autowired
	OmdbRestClient omnbRestClient;

	@Autowired
	OmdbService omnbService;

	@Autowired
	MovieRepository movieRepository;

	@RequestMapping(method = RequestMethod.GET)
	public String serachForm(Model model) {
		model.addAttribute("statusMessage", "No movies was selected for download.");
		return "list";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String getMovieList(@RequestParam(required = true) String title, String year, Model model) {
		if (title.isEmpty()) {
			model.addAttribute("statusMessage", "The Title field is required.");
		} else {
			// List<Movie> movies = omnbRestClient.getAllMoviesByTitleAndYear(title, year);
			List<Movie> movies = omnbRestClient.getFirstPageMoviesByTitleAndYear(title, year);
			if (Optional.ofNullable(movies).isPresent()) {
				model.addAttribute("movies", movies);
				model.addAttribute("title", title);
				model.addAttribute("year", year);
				model.addAttribute("statusMessage", "Now you can download selected movies.");
			}
			else {
				model.addAttribute("statusMessage", "Movies not found.");
			}
		}
		return "list";
	}

	@RequestMapping(value = "/downloadListUsual", method = RequestMethod.POST)
	public String downloadMovieListUsual(@RequestParam(required = true) String title, String year, Model model) {
		String statusMessage = omnbService.downloadMovieListUsual(title, year);
		model.addAttribute("statusMessage", statusMessage);
		return "list";
	}

	@RequestMapping(value = "/downloadListBatch", method = RequestMethod.POST)
	public String downloadMovieListBatch(@RequestParam(required = true) String title, String year, Model model) {
		String statusMessage = omnbService.downloadMovieListBatch(title, year);
		model.addAttribute("statusMessage", statusMessage);
		return "list";
	}

	@RequestMapping(value = "/showOne", method = RequestMethod.GET)
	public String getOneMovie(@RequestParam(required = true) String imdbId, Model model) {
		model.addAttribute("movie", omnbRestClient.getMovie(imdbId));
		model.addAttribute("statusMessage", "Now you can download selected movie.");
		return "one";
	}

	@RequestMapping(value = "/showOne", method = RequestMethod.POST)
	public String downloadMovie(@RequestParam(required = true) String imdbId, Model model) {
		String statusMessage = omnbService.downloadMovie(imdbId);
		model.addAttribute("movie", movieRepository.findByImdbId(imdbId));
		model.addAttribute("statusMessage", statusMessage);
		return "one";
	}

}
