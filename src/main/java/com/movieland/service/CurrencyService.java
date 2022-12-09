package com.movieland.service;

import com.movieland.dto.MovieDto;
import com.movieland.entity.CurrencyType;

public interface CurrencyService {

    void convert(MovieDto movieDto, CurrencyType currency);

}
