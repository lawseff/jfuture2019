package dev.jfuture.task.document.builder;

import static j2html.TagCreator.*;
import dev.jfuture.task.exception.DocumentException;
import dev.jfuture.task.exception.WebParsingException;
import dev.jfuture.task.service.MovieService;
import j2html.tags.ContainerTag;

import java.util.List;

public class DynamicsDocumentBuilder {

    private static final String TEMPLATE_FILE_NAME = "dynamics.html";
    private static final int BEGIN_YEAR = 2017;
    private static final int END_YEAR = 2019;
    private static final int GENRES_AMOUNT = 5;

    private static final String DYNAMICS_TAG = "$dynamics";
    private static final String USA = "USA";
    private static final String CHINA = "China";

    private DocumentBuilderHelper helper;
    private MovieService movieService;

    public DynamicsDocumentBuilder(DocumentBuilderHelper helper, MovieService movieService) {
        this.helper = helper;
        this.movieService = movieService;
    }

    public String getDynamicsInHtmlFormat() throws DocumentException {
        try {
            String content = helper.getLinesFromResourceFile(TEMPLATE_FILE_NAME);
            List<String> genres = movieService.getMostPopularGenres(BEGIN_YEAR, END_YEAR, GENRES_AMOUNT);

            ContainerTag dynamics = div();
            for (String genre : genres) {
                ContainerTag table = getTable(genre);
                ContainerTag caption = h3(genre);
                dynamics = dynamics.with(caption, table);
            }
            return content.replace(DYNAMICS_TAG, dynamics.render());
        } catch (WebParsingException e) {
            throw new DocumentException(e.getMessage(), e);
        }
    }

    private ContainerTag getTable(String genre) throws WebParsingException {
        ContainerTag firstRow = tr().with(td());
        ContainerTag secondRow = tr().with(td(USA));
        ContainerTag thirdRow = tr().with(td(CHINA));
        for (int year = BEGIN_YEAR; year <= END_YEAR; year++) {
            firstRow = firstRow.with(td(Integer.toString(year)));
            int americanMoviesAmount = movieService.getAmountOfAmericanMovies(genre, year);
            secondRow = secondRow.with(td(Integer.toString(americanMoviesAmount)));
            int chineseMoviesAmount = movieService.getAmountOfChineseMovies(genre, year);
            thirdRow = thirdRow.with(td(Integer.toString(chineseMoviesAmount)));
        }
        return table().with(firstRow, secondRow, thirdRow);
    }

}
