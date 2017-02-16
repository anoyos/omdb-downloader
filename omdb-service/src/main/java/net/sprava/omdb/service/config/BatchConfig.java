package net.sprava.omdb.service.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import net.sprava.omdb.model.Movie;
import net.sprava.omdb.service.batch.DbMovieWriter;
import net.sprava.omdb.service.batch.MovieProcessor;
import net.sprava.omdb.service.batch.OmdbRestMovieReader;

/**
 * 
 * @author Nikolay Koretskyy
 *
 */
@Configuration
@PropertySource("classpath:batch.properties")
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public OmdbRestMovieReader reader() {
		OmdbRestMovieReader reader = new OmdbRestMovieReader();
		return reader;
	}

	@Bean
	public ItemProcessor<Movie, Movie> processor() {
		MovieProcessor processor = new MovieProcessor();
		return processor;
	}

	@Bean
	public DbMovieWriter writer() {
		DbMovieWriter writer = new DbMovieWriter();
		return writer;
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.allowStartIfComplete(true)
				//.startLimit(Integer.MAX_VALUE)
				.<Movie, Movie>chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}

	@Bean
	public Job downloadMovieJob() {
		return jobBuilderFactory.get("downloadMovieJob")
				.start(step1())
				.build();
	}

	/*
	@Bean
	public StepScope stepScope() {
		StepScope stepScope = new StepScope();
		return stepScope;
	}
	*/
}
