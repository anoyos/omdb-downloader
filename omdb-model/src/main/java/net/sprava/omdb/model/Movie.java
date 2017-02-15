package net.sprava.omdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Nikolay Koretskyy
 *
 */
@Entity
@Table(name = "MOVIE")
public class Movie extends IdentityEntity {

	private String title;
	private String year;
	private String rated;
	private String released;
	private String runtime;
	private String genre;
	private String director;
	private String writer;
	private String actors;
	private String plot;
	private String language;
	private String country;
	private String awards;
	private String poster;
	private String metascore;
	private String imdbRating;
	private String imdbVotes;
	private String type;
	private String response;

	@JsonProperty("Title")
	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("Year")
	@Column(name = "YEAR")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@JsonProperty("Rated")
	@Column(name = "RATED")
	public String getRated() {
		return rated;
	}

	public void setRated(String rated) {
		this.rated = rated;
	}

	@JsonProperty("Released")
	@Column(name = "RELEASED")
	public String getReleased() {
		return released;
	}

	public void setReleased(String released) {
		this.released = released;
	}

	@JsonProperty("Runtime")
	@Column(name = "RUNTIME")
	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	@JsonProperty("Genre")
	@Column(name = "GENRE")
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	@JsonProperty("Director")
	@Column(name = "DIRECTOR")
	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	@JsonProperty("Writer")
	@Column(name = "WRITER", columnDefinition = "TEXT")
	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	@JsonProperty("Actors")
	@Column(name = "ACTORS", columnDefinition = "TEXT")
	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	@JsonProperty("Plot")
	@Column(name = "PLOT", columnDefinition = "TEXT")
	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	@JsonProperty("Language")
	@Column(name = "LANGUAGE")
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@JsonProperty("Country")
	@Column(name = "COUNTRY")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@JsonProperty("Awards")
	@Column(name = "AWARDS")
	public String getAwards() {
		return awards;
	}

	public void setAwards(String awards) {
		this.awards = awards;
	}

	@JsonProperty("Poster")
	@Column(name = "POSTER", columnDefinition = "TEXT")
	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	@JsonProperty("Metascore")
	@Column(name = "METASCORE")
	public String getMetascore() {
		return metascore;
	}

	public void setMetascore(String metascore) {
		this.metascore = metascore;
	}

	@JsonProperty("imdbRating")
	@Column(name = "IMDB_RATING")
	public String getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(String imdbRating) {
		this.imdbRating = imdbRating;
	}

	@JsonProperty("imdbVotes")
	@Column(name = "IMDB_VOTES")
	public String getImdbVotes() {
		return imdbVotes;
	}

	public void setImdbVotes(String imdbVotes) {
		this.imdbVotes = imdbVotes;
	}

	@JsonProperty("Type")
	@Column(name = "TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("Response")
	@Column(name = "RESPONSE")
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
