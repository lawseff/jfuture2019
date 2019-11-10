package dev.jfuture.task.parser.impl;

import dev.jfuture.task.entity.Movie;
import dev.jfuture.task.parser.AbstractMovieParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ChineseMovieParser extends AbstractMovieParser {

    // As Wikipedia articles can be changed, we have to use the specific versions.
    // Proper parsing of the films, released before 2017, might not be guaranteed
    private static final String SOURCE_2017 = "https://en.wikipedia.org/w/index.php?title=List_of_Chinese_films_of_2017&oldid=923009452";
    private static final String SOURCE_2018 = "https://en.wikipedia.org/w/index.php?title=List_of_Chinese_films_of_2018&oldid=900037799";
    private static final String SOURCE_2019 = "https://en.wikipedia.org/w/index.php?title=List_of_Chinese_films_of_2019&oldid=924830579";
    private static final String SOURCE_TEMPLATE = "https://en.wikipedia.org/wiki/List_of_Chinese_films_of_%d";

    private static final String[] TABLE_HEADER =
            { "Opening", "Title", "Director", "Cast", "Genre", "Notes", "Ref." };
    private static final String GENRE_DELIMITER = " / ";

    @Override
    protected Element getSource(int year) throws IOException {
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
        return Arrays.stream(TABLE_HEADER)
                .allMatch(text::contains);
    }

    @Override
    protected Optional<Movie> buildMovie(int year, Elements cells) {
        int lastColumnIndex = cells.size() - 1;
        int genreColumnIndex = lastColumnIndex - 2; // according to the header
        Element genreCell = cells.get(genreColumnIndex);
        String cellText = genreCell.text();
        if (cellText.isEmpty()) {
            return Optional.empty();
        }
        List<String> genresOfMovie = Arrays.asList(
                cellText.split(GENRE_DELIMITER)
        );
        // in the first task we don't need information about the director and rating
        Movie movie = new Movie(year, genresOfMovie, null, null);
        return Optional.of(movie);
    }

}
