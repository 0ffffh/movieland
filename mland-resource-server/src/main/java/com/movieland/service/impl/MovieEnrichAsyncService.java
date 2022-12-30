package com.movieland.service.impl;

import com.movieland.dto.CountryDto;
import com.movieland.dto.GenreDto;
import com.movieland.dto.MovieDto;
import com.movieland.dto.ReviewDto;
import com.movieland.entity.CurrencyType;
import com.movieland.entity.Movie;
import com.movieland.exception.MovieEnrichAsyncServiceException;
import com.movieland.mapper.MovieMapper;
import com.movieland.service.CountryService;
import com.movieland.service.CurrencyService;
import com.movieland.service.GenreService;
import com.movieland.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieEnrichAsyncService {
    public static final int TIMEOUT = 5;
    private final CountryService countryService;
    private final GenreService genreService;
    private final ReviewService reviewService;

    private final CurrencyService currencyService;

    private final MovieMapper movieMapper;
    private final Executor executor = Executors.newCachedThreadPool();

    public MovieDto buildMovieDtoAsync(Movie movie, CurrencyType currencyType) {
        MovieDto movieDto = movieMapper.movieToMovieDto(movie);
        CompletableFuture<List<CountryDto>> countries = getCountries(movie);
        CompletableFuture<List<GenreDto>> genres = getGenres(movie);
        CompletableFuture<List<ReviewDto>> reviews = getReviews(movie);
        CompletableFuture<Void> currency = calculateCurrency(movieDto, currencyType);

        try {
            CompletableFuture.allOf(countries, genres, reviews, currency).join();
            movieDto.setCountries(countries.get());
            movieDto.setGenres(genres.get());
            movieDto.setReviews(reviews.get());
        } catch (Exception e) {
            throw new MovieEnrichAsyncServiceException(e.getMessage());
        }
        log.info("Movie {} enriched", movie.getId());
        return movieDto;
    }

    private CompletableFuture<List<CountryDto>> getCountries(Movie movie) {
        return CompletableFuture.supplyAsync(() -> countryService.findByMovieId(movie.getId()), executor)
                .orTimeout(TIMEOUT, TimeUnit.SECONDS)
                .exceptionally(e -> {
                    log.info("Enrich countries error: {}", e.getMessage());
                    return Collections.emptyList();
                });
    }

    private CompletableFuture<List<GenreDto>> getGenres(Movie movie) {
        return CompletableFuture.supplyAsync(() -> genreService.findByMovieId(movie.getId()), executor)
                .orTimeout(TIMEOUT, TimeUnit.SECONDS)
                .exceptionally(e -> {
                    log.info("Enrich genres error: {}", e.getMessage());
                    return Collections.emptyList();
                });
    }

    private CompletableFuture<List<ReviewDto>> getReviews(Movie movie) {
        return CompletableFuture.supplyAsync(() -> reviewService.findByMovieId(movie.getId()), executor)
                .orTimeout(TIMEOUT, TimeUnit.SECONDS)
                .exceptionally(e -> {
                    log.info("Enrich reviews error: {}", e.getMessage());
                    return Collections.emptyList();
                });
    }

    public CompletableFuture<Void> calculateCurrency(MovieDto movieDto, CurrencyType currencyType) {
        log.info("Calculate currency type {}", currencyType.getCurrency());

        return currencyService.convert(movieDto, currencyType);
//        return CompletableFuture.runAsync(() -> currencyService.convert(movieDto, currencyType), executor)
//                .orTimeout(TIMEOUT, TimeUnit.SECONDS)
//                .handle((result, e) -> {
//                    if (e != null) {
//                        log.error("Calculate currency error: {}", e.getMessage());
//                    }
//                    return result;
//                });
    }

}
