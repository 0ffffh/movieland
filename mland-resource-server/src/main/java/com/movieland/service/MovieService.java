package com.movieland.service;

import com.movieland.dto.MovieDto;
import com.movieland.dto.MovieRequestDto;
import com.movieland.entity.CurrencyType;

import java.util.List;
import java.util.Map;

public interface MovieService {

    List<MovieDto> findAll(Map<String, String> param);

    List<MovieDto> findRandom(int count);

    List<MovieDto> findByGenre(int id);

    MovieDto findById(int id, CurrencyType currency);

    MovieDto add(MovieRequestDto movie);

    MovieDto update(int id, MovieRequestDto movie);

}
