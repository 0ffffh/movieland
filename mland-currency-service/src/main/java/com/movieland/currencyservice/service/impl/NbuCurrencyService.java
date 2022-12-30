package com.movieland.currencyservice.service.impl;

import com.movieland.currencyservice.entity.Currency;
import com.movieland.currencyservice.entity.CurrencyType;
import com.movieland.currencyservice.service.ExternalCurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NbuCurrencyService implements ExternalCurrencyService {
    private static final String CURRENCY_CACHE = "currency";
    public static final String NBU_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private final WebClient.Builder webClient;

    private List<Currency> getCurrencyRate() {
        return webClient.baseUrl(NBU_URL).build()
                .get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Currency>>() {
                })
                .doOnError(RuntimeException::new)
                .block();
    }

    @Override
    @Cacheable(value = CURRENCY_CACHE, key = "#currencyType", unless = "#result==null")
    public Currency getCurrency(CurrencyType currencyType) {
        List<Currency> listMono = getCurrencyRate();
        return listMono.stream()
                .filter(currency -> currency.getCurrency().equals(currencyType.getCurrency()))
                .findFirst().get();
    }

}

