package com.movieland.exception;

public class MovieNotFoundException extends RuntimeException {
    private static final String NO_MOVIE_BY_ID_MESSAGE = "Movie with id: %s not found";

    public MovieNotFoundException(String message) {
        super(message);
    }
    public MovieNotFoundException(int id) {
        super(String.format(NO_MOVIE_BY_ID_MESSAGE, id));
    }
}
