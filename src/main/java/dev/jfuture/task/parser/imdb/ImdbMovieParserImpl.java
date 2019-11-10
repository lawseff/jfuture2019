package dev.jfuture.task.parser.imdb;

import dev.jfuture.task.Movie;
import dev.jfuture.task.exception.WebParsingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImdbMovieParserImpl implements ImdbMovieParser {

    private static final String SOURCE = "https://www.imdb.com/chart/top";
    private static final String TABLE_CLASS = "chart full-width";
    private static final String TITLE_CLASS = "titleColumn";
    private static final String RATING_CLASS = "ratingColumn imdbRating";
    private static final String ROW_TAG = "tr";
    private static final String HYPERLINK_TAG = "a";

    private static final String DIRECTOR_ATTRIBUTE = "title";
    // directors are indicated in the attribute "title" of <a> tag
    private static final String DIRECTOR_LABEL = "(dir.)";

    private static final int HEADER_INDEX = 0;

    @Override
    public List<Movie> getTopMoviesOfDistinctDirectors(int amount) throws WebParsingException {
        Document document;
        try {
            document = Jsoup.connect(SOURCE).get();
        } catch (IOException e) {
            throw new WebParsingException(e.getMessage(), e);
        }
        Element body = document.body();
        Element table = body.getElementsByClass(TABLE_CLASS).first();
        Elements rows = table.select(ROW_TAG);
        removeHeader(rows);

        List<Movie> movies = new ArrayList<>(amount);
        for (int i = 0; movies.size() < 5; i++) {
            Element row = rows.get(i);
            Element titleAndDirectorAndCast = row.getElementsByClass(TITLE_CLASS)
                    .first()
                    .selectFirst(HYPERLINK_TAG); // <a> tag inside <td> tag
            String title = titleAndDirectorAndCast.text();
            String director = parseDirector(titleAndDirectorAndCast);
            String ratingValue = row.getElementsByClass(RATING_CLASS)
                    .first()
                    .text();
            BigDecimal rating = new BigDecimal(ratingValue);
            // In the second task we don't need year and genres of the movie
            Movie movie = new Movie(title, null, null, director, rating);

            Optional<Movie> movieWithSameDirectorOptional = getMovieWithDirector(movies, director);
            if (movieWithSameDirectorOptional.isPresent()) {
                Movie oldMove = movieWithSameDirectorOptional.get();
                if (shouldReplace(oldMove, movie)) {
                    int index = movies.indexOf(oldMove);
                    movies.set(index, movie);
                }
            } else {
                movies.add(movie);
            }
        }

        return movies;
    }

    private void removeHeader(Elements rows) {
        rows.remove(HEADER_INDEX);
    }

    private String parseDirector(Element titleAndDirector) {
        String directorAndCast = titleAndDirector.attr(DIRECTOR_ATTRIBUTE);
        int directorLabelIndex = directorAndCast.indexOf(DIRECTOR_LABEL);
        return directorAndCast.substring(0, directorLabelIndex).trim();
    }

    private Optional<Movie> getMovieWithDirector(List<Movie> movies, String director) {
        return movies.stream()
                .filter(m -> m.getDirector().equals(director))
                .findFirst();
    }

    private boolean shouldReplace(Movie oldMovie, Movie movie) {
        return movie.getRating()
                .compareTo(oldMovie.getRating()) > 0;
    }

}
