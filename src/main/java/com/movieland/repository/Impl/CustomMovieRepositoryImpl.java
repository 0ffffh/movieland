package com.movieland.repository.Impl;

import com.movieland.entity.Movie;
import com.movieland.repository.CustomMovieRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomMovieRepositoryImpl implements CustomMovieRepository {
    private final static String SELECT_RANDOM_MOVIES = "SELECT m FROM Movie m ORDER BY random()";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Movie> findRandom(int count) {
        return entityManager
                .createQuery(SELECT_RANDOM_MOVIES, Movie.class)
                .setMaxResults(count).getResultList();
    }

}
