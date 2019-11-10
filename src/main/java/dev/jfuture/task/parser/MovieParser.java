package dev.jfuture.task.parser;

import dev.jfuture.task.entity.Movie;
import dev.jfuture.task.exception.WebParsingException;

import java.util.List;

public interface MovieParser {

    List<Movie> getMoviesByYear(int year) throws WebParsingException;

}
