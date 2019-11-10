package dev.jfuture.task.parser.imdb;

import dev.jfuture.task.Movie;
import dev.jfuture.task.exception.WebParsingException;

import java.util.List;

public interface ImdbMovieParser {

    List<Movie> getTopMoviesOfDistinctDirectors(int amount) throws WebParsingException;

}
