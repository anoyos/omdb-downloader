package net.sprava.omdb.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.sprava.omdb.persistence.MovieRepository;
import net.sprava.omdb.service.OmnbRestClient;
import net.sprava.omdb.service.OmnbService;

/**
 *
 * @author Nikolay Koretskyy
 *
 */
@Controller
public class OmdbController {

	@Autowired
	OmnbRestClient omnbRestClientImpl;

	@Autowired
	OmnbService omnbServiceImpl;

	@Autowired
	MovieRepository movieRepository;

	@RequestMapping(method = RequestMethod.GET)
	public String selectYear(Model model) {
		model.addAttribute("statusMessage", "Nothing selected for download.");
		return "list";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String getMovieList(@RequestParam(required = true) String keyword, Model model) {
		model.addAttribute("movies", omnbRestClientImpl.getMoviesByKeyword(keyword));
		model.addAttribute("keyword", keyword);
		model.addAttribute("statusMessage", "Now you can download selected movies.");
		return "list";
	}

	@RequestMapping(value = "/downloadList", method = RequestMethod.POST)
	public String downloadMovieList(@RequestParam(required = true) String keyword, Model model) {
		String statusMessage = omnbServiceImpl.downloadMovieList(keyword);
		model.addAttribute("statusMessage", statusMessage);
		return "list";
	}

	@RequestMapping(value = "/showOne", method = RequestMethod.GET)
	public String getOneMovie(@RequestParam(required = true) String imdbId, Model model) {
		model.addAttribute("movie", omnbRestClientImpl.getMovie(imdbId));
		model.addAttribute("statusMessage", "Now you can download selected movie.");
		return "one";
	}

	@RequestMapping(value = "/showOne", method = RequestMethod.POST)
	public String downloadMovie(@RequestParam(required = true) String imdbId, Model model) {
		String statusMessage = omnbServiceImpl.downloadMovie(imdbId);
		model.addAttribute("movie", movieRepository.findByImdbId(imdbId));
		model.addAttribute("statusMessage", statusMessage);
		return "one";
	}

}
