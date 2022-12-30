package com.movieland.currencyservice.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CurrencyType {
    UAH("UAH"),
    USD("USD"),
    EUR("EUR");

    @Getter
    private final String currency;
}
