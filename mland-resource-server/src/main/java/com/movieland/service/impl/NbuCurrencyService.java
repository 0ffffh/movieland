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
    public static final String NBU_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";


    private final WebClient.Builder webClient;

    @Override
    @Cacheable(CURRENCY_CACHE)
    public List<Currency> getCurrencyRate() {
        return webClient.baseUrl(NBU_URL).build().get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Currency>>() {
                }).doOnError(CurrencyServiceException::new)
                .block();
    }

}

