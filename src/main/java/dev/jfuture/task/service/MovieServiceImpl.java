package dev.jfuture.task.service;

import dev.jfuture.task.exception.WebParsingException;
import dev.jfuture.task.calculator.GenreCalculator;
import dev.jfuture.task.entity.Movie;
import dev.jfuture.task.parser.wikipedia.WikipediaMovieParser;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieServiceImpl implements MovieService {

    private WikipediaMovieParser americanMovieParser;
    private WikipediaMovieParser chineseMovieParser;
    private GenreCalculator genreCalculator;

    public MovieServiceImpl(WikipediaMovieParser americanMovieParser, WikipediaMovieParser chineseMovieParser,
                            GenreCalculator genreCalculator) {
        this.americanMovieParser = americanMovieParser;
        this.chineseMovieParser = chineseMovieParser;
        this.genreCalculator = genreCalculator;
    }

    @Override
    public List<String> getMostPopularGenres(int beginYear, int endYear, int amount) throws WebParsingException {
        List<Movie> allMovies = new ArrayList<>();
        for (int year = beginYear; year <= endYear; year++) {
            List<Movie> americanMovies = americanMovieParser.getMoviesByYear(year);
            List<Movie> chineseMovies = chineseMovieParser.getMoviesByYear(year);
            allMovies.addAll(americanMovies);
            allMovies.addAll(chineseMovies);
        }
        return genreCalculator.getGenresAndAmount(allMovies)
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .limit(amount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public int getAmountOfAmericanMovies(String genre, int year) throws WebParsingException {
        List<Movie> americanMovies = americanMovieParser.getMoviesByYear(year);
        return getAmountOfMovies(genre, americanMovies);
    }

    @Override
    public int getAmountOfChineseMovies(String genre, int year) throws WebParsingException {
        List<Movie> chineseMovies = chineseMovieParser.getMoviesByYear(year);
        return getAmountOfMovies(genre, chineseMovies);
    }

    private int getAmountOfMovies(String genre, List<Movie> movies) {
        return (int) movies.stream()
                .filter(m -> hasGenre(m, genre))
                .count();
    }

    private boolean hasGenre(Movie movie, String genre) {
        return movie.getGenres()
                .stream()
                .anyMatch(genre::equals);
    }

}
