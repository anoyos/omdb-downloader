package net.sprava.omdb.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.sprava.omdb.service.OmnbRestClient;

/**
 *
 * @author Nikolay Koretskyy
 *
 */
@Controller
public class OmdbController {

	@Autowired
	OmnbRestClient omnbRestClient;

	@RequestMapping(method = RequestMethod.GET)
	public String selectYear(Model model) {
		return "ui";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String outputContent(@RequestParam(required = true) String keyword, Model model) {
		model.addAttribute("movies", omnbRestClient.getMoviesByKeyword(keyword));
		return "ui";
	}

	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public String downloadContent(@RequestParam(required = true) String keyword, Model model) {
		return "ui";
	}

}
