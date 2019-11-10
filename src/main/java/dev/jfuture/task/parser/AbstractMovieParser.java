package dev.jfuture.task.parser;

import dev.jfuture.task.entity.Movie;
import dev.jfuture.task.exception.WebParsingException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractMovieParser implements MovieParser {

    private static final String TABLE_TAG = "table";
    private static final String ROW_TAG = "tr";
    private static final String CELL_TAG = "td";
    private static final int HEADER_INDEX = 0;

    public List<Movie> getMoviesByYear(int year) throws WebParsingException {
        List<Movie> movies = new ArrayList<>();

        Element source;
        try {
            source = getSource(year);
        } catch (IOException e) {
            throw new WebParsingException(e.getMessage(), e);
        }

        Elements tables = source.select(TABLE_TAG)
                .stream()
                .filter(this::isMovieTable)
                .collect(Collectors.toCollection(Elements::new));
        for (Element table : tables) {
            Elements rows = table.select(ROW_TAG);
            removeHeader(rows);
            for (Element row : rows) {
                Elements cells = row.select(CELL_TAG);
                Optional<Movie> movieOptional = buildMovie(year, cells);
                if (movieOptional.isPresent()) {
                    Movie movie = movieOptional.get();
                    movies.add(movie);
                }
            }
        }

        return movies;
    }

    protected abstract Element getSource(int year) throws IOException;

    protected abstract boolean isMovieTable(Element table);

    protected abstract Optional<Movie> buildMovie(int year, Elements cells);

    private void removeHeader(Elements rows) {
        rows.remove(HEADER_INDEX);
    }

}
