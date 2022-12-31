package com.movieland.service;

import com.movieland.dto.MovieDto;
import com.movieland.entity.CurrencyType;

import java.util.concurrent.CompletableFuture;

public interface CurrencyService {

CompletableFuture<Void> convert(MovieDto movieDto, CurrencyType currency);

}
