package com.movieland.service.impl;

import com.movieland.dto.GenreDto;
import com.movieland.entity.Genre;
import com.movieland.mapper.GenreMapper;
import com.movieland.repository.GenreRepository;
import com.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultGenreService implements GenreService {
    private static final String GENRE_CACHE = "genres";

    private final GenreMapper genreMapper;
    private final GenreRepository genreRepository;

    @Override
    @Cacheable(GENRE_CACHE)
    @Transactional(readOnly = true)
    public List<GenreDto> findAll() {
        List<Genre> genres = genreRepository.findAll();
        log.info("Find {} genres", genres.size());
        return genreMapper.genreToGenreDtoList(genres);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAllGenresById(List<Integer> ids) {
        return genreRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findByMovieId(int movieId) {
        return genreMapper.genreToGenreDtoList(genreRepository.findByMovies_Id_MovieId(movieId));
    }

}
