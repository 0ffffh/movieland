package com.movieland.service.impl;

import com.movieland.dto.CountryDto;
import com.movieland.entity.Country;
import com.movieland.mapper.CountryMapper;
import com.movieland.repository.CountryRepository;
import com.movieland.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultCountryService implements CountryService {
    private static final String COUNTRY_CACHE = "countries";

    private final CountryMapper countryMapper;
    private final CountryRepository countryRepository;

    @Override
    @Cacheable(COUNTRY_CACHE)
    @Transactional(readOnly = true)
    public List<CountryDto> findAll() {
        List<Country> countries = countryRepository.findAll();
        log.info("Find {} countries", countries.size());
        return countryMapper.countriesToCountryDtoList(countries);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Country> findAllCountriesById(List<Integer> ids) {
        return countryRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryDto> findByMovieId(int movieId) {
        return countryMapper.countriesToCountryDtoList(countryRepository.findByMovies_Id_MovieId(movieId));
    }

}
