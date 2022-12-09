package com.movieland.service.impl;

import com.movieland.entity.Currency;
import com.movieland.exception.CurrencyServiceException;
import com.movieland.service.ExternalCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NbuCurrencyService implements ExternalCurrencyService {
    private static final String CURRENCY_CACHE = "currency";

    private final WebClient webClient;

    @Override
    @Cacheable(CURRENCY_CACHE)
    public List<Currency> getCurrencyRate() {
        return webClient.get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Currency>>() {
                }).doOnError(CurrencyServiceException::new)
                .block();
    }

}

