package dev.jfuture.task;

import dev.jfuture.task.document.DocumentSaver;
import dev.jfuture.task.document.builder.DocumentBuilderHelper;
import dev.jfuture.task.document.builder.DynamicsDocumentBuilder;
import dev.jfuture.task.exception.DocumentException;
import dev.jfuture.task.calculator.GenreCalculator;
import dev.jfuture.task.calculator.GenreCalculatorImpl;
import dev.jfuture.task.parser.MovieParser;
import dev.jfuture.task.parser.impl.AmericanMovieParser;
import dev.jfuture.task.parser.impl.ChineseMovieParser;
import dev.jfuture.task.service.MovieService;
import dev.jfuture.task.service.MovieServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TaskSolver {

    private static final String DYNAMICS_FILE = "dynamics.html";

    public static void main(String[] args) {
        MovieParser americanMovieParser = new AmericanMovieParser();
        MovieParser chineseMovieParser = new ChineseMovieParser();
        GenreCalculator genreCalculator = new GenreCalculatorImpl();
        MovieService movieService = new MovieServiceImpl(americanMovieParser, chineseMovieParser, genreCalculator);
        DocumentBuilderHelper helper = new DocumentBuilderHelper();
        DynamicsDocumentBuilder dynamicsDocumentBuilder = new DynamicsDocumentBuilder(helper, movieService);

        try {
            String dynamicsContent = dynamicsDocumentBuilder.getDynamicsInHtmlFormat();
            DocumentSaver saver = new DocumentSaver();
            saver.save(DYNAMICS_FILE, dynamicsContent);
        } catch (DocumentException e) {
            Logger logger = LogManager.getLogger(TaskSolver.class);
            logger.error(e.getMessage(), e);
        }
    }

}
