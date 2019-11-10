package dev.jfuture.task;

import dev.jfuture.task.document.DocumentSaver;
import dev.jfuture.task.document.builder.DirectorsDocumentBuilder;
import dev.jfuture.task.document.builder.DocumentBuilderHelper;
import dev.jfuture.task.document.builder.DynamicsDocumentBuilder;
import dev.jfuture.task.exception.DocumentException;
import dev.jfuture.task.calculator.GenreCalculator;
import dev.jfuture.task.calculator.GenreCalculatorImpl;
import dev.jfuture.task.parser.imdb.ImdbMovieParser;
import dev.jfuture.task.parser.imdb.ImdbMovieParserImpl;
import dev.jfuture.task.parser.wikipedia.WikipediaMovieParser;
import dev.jfuture.task.parser.wikipedia.impl.AmericanMovieParser;
import dev.jfuture.task.parser.wikipedia.impl.ChineseMovieParser;
import dev.jfuture.task.service.MovieService;
import dev.jfuture.task.service.MovieServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TaskSolver {

    private static final String DYNAMICS_FILE = "dynamics.html";
    private static final String DIRECTORS_FILE = "directors.html";

    private static final DocumentBuilderHelper BUILDER_HELPER = new DocumentBuilderHelper();
    private static final DocumentSaver DOCUMENT_SAVER = new DocumentSaver();

    public static void main(String[] args) {
        try {
            solveFirstTask();
            solveSecondTask();
        } catch (DocumentException e) {
            Logger logger = LogManager.getLogger(TaskSolver.class);
            logger.error(e.getMessage(), e);
        }
    }

    private static void solveFirstTask() throws DocumentException {
        WikipediaMovieParser americanMovieParser = new AmericanMovieParser();
        WikipediaMovieParser chineseMovieParser = new ChineseMovieParser();
        GenreCalculator genreCalculator = new GenreCalculatorImpl();
        MovieService movieService = new MovieServiceImpl(americanMovieParser, chineseMovieParser, genreCalculator);
        DynamicsDocumentBuilder dynamicsDocumentBuilder = new DynamicsDocumentBuilder(BUILDER_HELPER, movieService);
        String dynamicsContent = dynamicsDocumentBuilder.getDynamicsInHtmlFormat();
        DOCUMENT_SAVER.save(DYNAMICS_FILE, dynamicsContent);
    }

    private static void solveSecondTask() throws DocumentException {
        ImdbMovieParser parser = new ImdbMovieParserImpl();
        DirectorsDocumentBuilder directorsDocumentBuilder = new DirectorsDocumentBuilder(BUILDER_HELPER, parser);
        String directorsContent = directorsDocumentBuilder.getTopDirectorsInHtmlFormat();
        DOCUMENT_SAVER.save(DIRECTORS_FILE, directorsContent);
    }

}
