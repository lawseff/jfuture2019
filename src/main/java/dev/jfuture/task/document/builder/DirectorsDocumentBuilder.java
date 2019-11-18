package dev.jfuture.task.document.builder;

import static j2html.TagCreator.*;
import dev.jfuture.task.Movie;
import dev.jfuture.task.exception.DocumentException;
import dev.jfuture.task.exception.WebParsingException;
import dev.jfuture.task.parser.imdb.ImdbMovieParser;
import j2html.tags.ContainerTag;
import java.util.List;

public class DirectorsDocumentBuilder {

    private static final String TEMPLATE_FILE_NAME = "directors.html";
    private static final int DIRECTORS_AMOUNT = 5;
    private static final String DIRECTORS_TAG = "$directors";
    private static final ContainerTag TABLE = table(
            tr(
                    td(),
                    td("Director"),
                    td("Film"),
                    td("Rating")
            )
    );

    private DocumentBuilderHelper helper;
    private ImdbMovieParser imdbMovieParser;

    public DirectorsDocumentBuilder(DocumentBuilderHelper helper, ImdbMovieParser imdbMovieParser) {
        this.helper = helper;
        this.imdbMovieParser = imdbMovieParser;
    }

    public String getTopDirectorsInHtmlFormat() throws DocumentException {
        List<Movie> topRatedMovies;
        try {
            topRatedMovies = imdbMovieParser.getTopMoviesOfDistinctDirectors(5);
        } catch (WebParsingException e) {
            throw new DocumentException(e.getMessage(), e);
        }
        ContainerTag table = TABLE;
        for (int i = 0; i < DIRECTORS_AMOUNT; i++) {
            Movie movie = topRatedMovies.get(i);
            ContainerTag number = td(Integer.toString(i + 1));
            ContainerTag director = td(movie.getDirector());
            ContainerTag title = td(movie.getTitle());
            ContainerTag rating = td(movie.getRating().toString());
            ContainerTag row = tr().with(number, director, title, rating);
            table = table.with(row);
        }
        String content = helper.getLinesFromResourceFile(TEMPLATE_FILE_NAME);
        return content.replace(DIRECTORS_TAG, table.render());
    }

}
