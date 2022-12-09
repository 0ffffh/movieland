package com.movieland.repository;

import com.movieland.entity.Movie;

import java.util.List;

public interface CustomMovieRepository {
    List<Movie> findRandom(int count);
}
