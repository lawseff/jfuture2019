package dev.jfuture.task.parser.wikipedia.impl;

import dev.jfuture.task.entity.Movie;
import dev.jfuture.task.parser.wikipedia.AbstractMovieParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AmericanMovieParser extends AbstractMovieParser {

    // As structure of some Wikipedia articles can be changed, we have to use the specific versions.
    // Proper parsing of the films, released before 2017, might not be guaranteed
    private static final String SOURCE_2017 =
            "https://en.wikipedia.org/w/index.php?title=List_of_American_films_of_2017&oldid=925022483";
    private static final String SOURCE_2018 =
            "https://en.wikipedia.org/w/index.php?title=List_of_American_films_of_2018&oldid=924688872";
    private static final String SOURCE_2019 =
            "https://en.wikipedia.org/w/index.php?title=List_of_American_films_of_2019&oldid=925237075";
    private static final String SOURCE_TEMPLATE = "https://en.wikipedia.org/List_of_American_films_of_%d";

    private static final String[] TABLE_HEADER =
            { "Opening", "Title", "Production company", "Cast and crew", "Genre", "Ref." };
    private static final String[] OLD_TABLE_HEADER =
            { "Opening", "Title", "Studio", "Cast and crew", "Genre", "Ref." };
    private static final String GENRE_DELIMITER = ", ";

    @Override
    protected Document getSource(int year) throws IOException {
        String source;
        switch (year) {
            case 2017:
                source = SOURCE_2017;
                break;
            case 2018:
                source = SOURCE_2018;
                break;
            case 2019:
                source = SOURCE_2019;
                break;
            default:
                source = String.format(SOURCE_TEMPLATE, year);
        }
        return Jsoup.connect(source).get();
    }

    @Override
    protected boolean isMovieTable(Element table) {
        String text = table.text();
        return Arrays.stream(TABLE_HEADER).allMatch(text::contains) ||
                Arrays.stream(OLD_TABLE_HEADER).allMatch(text::contains);
    }

    @Override
    protected Optional<Movie> buildMovie(int year, Elements cells) {
        int lastColumnIndex = cells.size() - 1;
        int genreColumnIndex = lastColumnIndex - 1; // according to the header
        Element genreCell = cells.get(genreColumnIndex);
        String cellText = genreCell.text();
        List<String> genresOfMovie = Arrays.asList(
                cellText.split(GENRE_DELIMITER)
        );
        // in the first task we don't need information about the director and rating
        Movie movie = new Movie(year, genresOfMovie, null, null);
        return Optional.of(movie);
    }


}
