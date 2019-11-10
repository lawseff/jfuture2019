package dev.jfuture.task.entity;

import java.util.List;

public class Movie {

    private int year;
    private List<String> genres;
    private String director;
    private Rating rating;

    public Movie() {

    }

    public Movie(int year, List<String> genres, String director, Rating rating) {
        this.year = year;
        this.genres = genres;
        this.director = director;
        this.rating = rating;
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

    public Rating getRating() {
        return rating;
    }

}
