package com.movieland.service;

import com.movieland.dto.CountryDto;
import com.movieland.entity.Country;

import java.util.List;

public interface CountryService {
    List<CountryDto> findAll();

    List<Country> findAllCountriesById(List<Integer> ids);

    List<CountryDto> findByMovieId(int id);
}
