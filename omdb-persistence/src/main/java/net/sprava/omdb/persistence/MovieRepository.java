package net.sprava.omdb.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import net.sprava.omdb.model.Movie;

/**
 * 
 * @author Nikolay Koretskyy
 *
 */
public interface MovieRepository extends JpaRepository<Movie, Integer> {

	Movie findByImdbId(String imdbId);
}
