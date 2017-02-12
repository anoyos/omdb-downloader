package net.sprava.omdb.ui.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Entry point to start Spring Boot Application
 * 
 * @author Nikolay Koretskyy
 *
 */
@SpringBootApplication
@EnableJpaRepositories("net.sprava.omdb")
@ComponentScan("net.sprava.omdb")
public class OmdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmdbApplication.class, args);
	}

}
