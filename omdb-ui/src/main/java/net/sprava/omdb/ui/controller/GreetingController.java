package net.sprava.omdb.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.sprava.omdb.service.OmnbRestClient;

/**
 *
 * @author Nikolay Koretskyy
 *
 */
@Controller
public class GreetingController {

	@Autowired
	OmnbRestClient omnbRestClient;
	
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);

        omnbRestClient.getMovie();

        return "greeting";
    }

}
