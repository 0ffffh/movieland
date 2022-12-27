package com.movieland.service.impl;

import com.movieland.dto.MovieDto;
import com.movieland.entity.CurrencyType;
import com.movieland.service.CurrencyService;
import com.movieland.service.ExternalCurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultCurrencyService implements CurrencyService {

    private final ExternalCurrencyService service;

    @Override
    public void convert(MovieDto movieDto, CurrencyType currencyType) {
        log.info("Currency type {}", currencyType.getCurrency());
        if (CurrencyType.UAH != currencyType) {
            service.getCurrencyRate().stream()
                    .filter(currency -> currency.getCurrency().equals(currencyType.getCurrency()))
                    .findFirst().ifPresent(currency -> {
                        double price = movieDto.getPrice();
                        double rate = currency.getRate();
                        double newPrice = BigDecimal.valueOf(price / rate)
                                .setScale(2, RoundingMode.HALF_UP)
                                .doubleValue();

                        movieDto.setPrice(newPrice);
                    });
        }
    }

}

