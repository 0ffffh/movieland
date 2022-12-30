package com.movieland.service;

import com.movieland.dto.MovieDto;
import com.movieland.entity.CurrencyType;

import java.util.concurrent.CompletableFuture;

public interface CurrencyService {

//    void convert(MovieDto movieDto, CurrencyType currency);
CompletableFuture<Void> convert(MovieDto movieDto, CurrencyType currency);

}
