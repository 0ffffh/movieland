package com.movieland.service;

import com.movieland.dto.GenreDto;
import com.movieland.entity.Genre;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();

    List<Genre> findAllGenresById(List<Integer> ids);

    List<GenreDto> findByMovieId(int id);
}
