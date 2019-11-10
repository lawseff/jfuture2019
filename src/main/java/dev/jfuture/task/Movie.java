package dev.jfuture.task;

import java.math.BigDecimal;
import java.util.List;

public class Movie {

    private String title;
    private Integer year;
    private List<String> genres;
    private String director;
    private BigDecimal rating;

    public Movie() {

    }

    public Movie(String title, Integer year, List<String> genres, String director, BigDecimal rating) {
        this.title = title;
        this.year = year;
        this.genres = genres;
        this.director = director;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getDirector() {
        return director;
    }

    public BigDecimal getRating() {
        return rating;
    }

}
