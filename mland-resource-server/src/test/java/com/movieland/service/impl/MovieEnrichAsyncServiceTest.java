package com.movieland.service.impl;

import com.movieland.AbstractBaseITest;
import com.movieland.dto.CountryDto;
import com.movieland.dto.GenreDto;
import com.movieland.dto.MovieDto;
import com.movieland.dto.ReviewDto;
import com.movieland.dto.UserDto;
import com.movieland.entity.CurrencyType;
import com.movieland.entity.Movie;
import com.movieland.repository.MovieRepository;
import com.movieland.service.CountryService;
import com.movieland.service.GenreService;
import com.movieland.service.ReviewService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@TestInstance(PER_CLASS)
class MovieEnrichAsyncServiceTest extends AbstractBaseITest {

    @Autowired
    private MovieEnrichAsyncService enrichService;
    @MockBean
    private MovieRepository movieRepository;
    @MockBean
    private CountryService countryService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private ReviewService reviewService;

    private Movie movie;
    private List<CountryDto> countries;
    private List<GenreDto> genres;
    private List<ReviewDto> reviews;

    @BeforeAll
    public void setUp() {
        movie = new Movie();
        movie.setId(1);
        movie.setNameNative("TEST");
        movie.setNameRu("TEST_RU");
        movie.setDescription("DESCRIPTION");
        movie.setPrice(123.45);
        movie.setPicturePath("http");
        movie.setReleaseDate(LocalDate.EPOCH);

        countries = List.of(new CountryDto(1, "Украина"));
        genres = List.of(new GenreDto(1, "Криминал"));
        reviews = List.of(new ReviewDto(1, new UserDto(1, "NickName"), "TEXT"));

    }

    @Test
    @DisplayName("Async enrich movie")
    void buildMovieDtoAsync() {
        when(movieRepository.findById(anyInt())).thenReturn(Optional.of(movie));
        when(countryService.findByMovieId(anyInt())).thenReturn(countries);
        when(genreService.findByMovieId(anyInt())).thenReturn(genres);
        when(reviewService.findByMovieId(anyInt())).thenReturn(reviews);

        MovieDto testMovie = enrichService.buildMovieDtoAsync(movie, CurrencyType.UAH);

        assertEquals(1, testMovie.getId());
        assertEquals("TEST", testMovie.getNameNative());
        assertEquals("TEST_RU", testMovie.getNameRu());
        assertEquals("DESCRIPTION", testMovie.getDescription());
        assertEquals(123.45, testMovie.getPrice());
        assertEquals("http", testMovie.getPicturePath());
        assertEquals("1970", testMovie.getYearOfRelease().format(DateTimeFormatter.ofPattern("yyyy")));
        assertEquals(countries, testMovie.getCountries());
        assertEquals(genres, testMovie.getGenres());
        assertEquals(reviews, testMovie.getReviews());

    }

    @Test
    @DisplayName("Async enrich movie services exceptions")
    void buildMovieDtoAsyncEx() {
        when(movieRepository.findById(anyInt())).thenReturn(Optional.of(movie));
        when(countryService.findByMovieId(anyInt())).thenThrow(new RuntimeException());
        when(genreService.findByMovieId(anyInt())).thenThrow(new RuntimeException());
        when(reviewService.findByMovieId(anyInt())).thenThrow(new RuntimeException());

        MovieDto testMovie = enrichService.buildMovieDtoAsync(movie, CurrencyType.UAH);

        assertEquals(1, testMovie.getId());
        assertEquals("TEST", testMovie.getNameNative());
        assertEquals("TEST_RU", testMovie.getNameRu());
        assertEquals("DESCRIPTION", testMovie.getDescription());
        assertEquals(123.45, testMovie.getPrice());
        assertEquals("http", testMovie.getPicturePath());
        assertEquals("1970", testMovie.getYearOfRelease().format(DateTimeFormatter.ofPattern("yyyy")));
        assertEquals(Collections.emptyList(), testMovie.getCountries());
        assertEquals(Collections.emptyList(), testMovie.getGenres());
        assertEquals(Collections.emptyList(), testMovie.getReviews());

    }
}