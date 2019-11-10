package dev.jfuture.task.calculator;

import dev.jfuture.task.Movie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenreCalculatorImpl implements GenreCalculator {

    @Override
    public Map<String, Integer> getGenresAndAmount(List<Movie> movies) {
        Map<String, Integer> genres = new HashMap<>();
        for (Movie movie : movies) {
            for (String genre : movie.getGenres()) {
                int amount = genres.getOrDefault(genre, 0);
                int newAmount = amount + 1;
                genres.put(genre, newAmount);
            }
        }
        return genres;
    }

}
