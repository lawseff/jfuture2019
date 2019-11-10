package dev.jfuture.task.parser.wikipedia;

import dev.jfuture.task.entity.Movie;
import dev.jfuture.task.exception.WebParsingException;

import java.util.List;

public interface WikipediaMovieParser {

    List<Movie> getMoviesByYear(int year) throws WebParsingException;

}
