package dev.jfuture.task.service;

import dev.jfuture.task.exception.WebParsingException;
import java.util.List;

public interface MovieService {

    List<String> getMostPopularGenres(int beginYear, int endYear, int amount) throws WebParsingException;

    int getAmountOfAmericanMovies(String genre, int year) throws WebParsingException;

    int getAmountOfChineseMovies(String genre, int year) throws WebParsingException;

}
