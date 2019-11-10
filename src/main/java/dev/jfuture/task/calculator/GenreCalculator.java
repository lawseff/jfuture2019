package dev.jfuture.task.calculator;

import dev.jfuture.task.entity.Movie;
import java.util.List;
import java.util.Map;

public interface GenreCalculator {

    Map<String, Integer> getGenresAndAmount(List<Movie> movies);

}
