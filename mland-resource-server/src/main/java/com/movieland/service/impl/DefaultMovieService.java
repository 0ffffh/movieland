package com.movieland.service.impl;

import com.movieland.dto.MovieDto;
import com.movieland.dto.MovieRequestDto;
import com.movieland.entity.Country;
import com.movieland.entity.CurrencyType;
import com.movieland.entity.Genre;
import com.movieland.entity.Movie;
import com.movieland.exception.MovieNotFoundException;
import com.movieland.mapper.MovieMapper;
import com.movieland.repository.MovieRepository;
import com.movieland.service.CountryService;
import com.movieland.service.GenreService;
import com.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultMovieService implements MovieService {
    private static final String MOVIE_CACHE = "movies";

    private final MovieRepository movieRepository;
    private final CountryService countryService;
    private final GenreService genreService;

    private final MovieEnrichAsyncService enrichService;

    private final MovieMapper movieMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> findAll(Map<String, String> param) {
        List<Sort.Order> orders = param.entrySet().stream().map(entry ->
                new Sort.Order(entry.getValue().equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                        Sort.Direction.ASC : Sort.Direction.DESC, entry.getKey())).toList();
        List<Movie> movies = movieRepository.findAll(Sort.by(orders));
        log.info("Found {} movies", movies.size());
        return movieMapper.moviesToShortMovieDtoList(movies);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> findRandom(int count) {
        List<Movie> movies = movieRepository.findRandom(count);
        log.info("Find {} random movies", movies.size());
        return movieMapper.moviesToShortMovieDtoList(movies);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> findByGenre(int genreId) {
        return movieMapper.moviesToShortMovieDtoList(movieRepository.findByGenres_Id_GenreId(genreId));
    }

    @Override
    @Cacheable(cacheNames = MOVIE_CACHE, key = "#id", condition = "#currency.name().equals('UAH')")
    @Transactional(readOnly = true)
    public MovieDto findById(int id, CurrencyType currency) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
        log.info("Find movie with id = {}", id);
        return enrichService.buildMovieDtoAsync(movie, currency);
    }

    @Override
    @Transactional
    public MovieDto add(MovieRequestDto movieDto) {
        Movie movie = movieMapper.addMovie(movieDto);
        Movie savedMovie = movieRepository.save(movie);
        enrich(savedMovie, movieDto);
        log.info("Added movie id = {}", savedMovie.getId());
        return movieMapper.toMovieDto(savedMovie);
    }

    @Override
    @CachePut(cacheNames = MOVIE_CACHE, key = "#id")
    @Transactional
    public MovieDto update(int id, MovieRequestDto movieDto) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));

        enrich(movie, movieDto);
        movieMapper.update(movie, movieDto);
        log.info("Movie {} updated", id);
        return movieMapper.toMovieDto(movieRepository.save(movie));
    }

    @Override
    public List<MovieDto> findByTitle(String title, Pageable pageable) {
        if(title ==null || title.isBlank()){
            return movieMapper.moviesToShortMovieDtoList(movieRepository.findAll());
        }
        return movieMapper.moviesToShortMovieDtoList(movieRepository.findByTitle(title, pageable));
    }

    private void enrich(Movie movie, MovieRequestDto movieDto) {
        List<Genre> genres = genreService.findAllGenresById(movieDto.getGenreIds());
        List<Country> countries = countryService.findAllCountriesById(movieDto.getCountryIds());
        movie.updateCountries(countries);
        movie.updateGenres(genres);
    }

}
