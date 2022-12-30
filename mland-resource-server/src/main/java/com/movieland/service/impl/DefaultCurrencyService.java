package com.movieland.service.impl;

import com.movieland.dto.MovieDto;
import com.movieland.entity.Currency;
import com.movieland.entity.CurrencyType;
import com.movieland.service.CurrencyService;
import com.movieland.service.ExternalCurrencyService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultCurrencyService implements CurrencyService {

    private final ExternalCurrencyService externalCurrencyService;

    @Override
    @CircuitBreaker(name = "nbu", fallbackMethod = "getFallBack")
    public void convert(MovieDto movieDto, CurrencyType currencyType) {
        log.info("Currency type {}", currencyType.getCurrency());
        if (CurrencyType.UAH != currencyType) {
            Currency currency = externalCurrencyService.getCurrency(currencyType);
            double price = movieDto.getPrice();
            double rate = currency.getRate();
            double newPrice = BigDecimal.valueOf(price / rate)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();

            movieDto.setPrice(newPrice);
        }
    }

    public void getFallBack(MovieDto movieDto, CurrencyType currencyType, Throwable t) {
        log.error("Currency service unavailable. Set default currency type UAH", t.getMessage());
    }
}

