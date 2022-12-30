package com.movieland.currencyservice.service;


import com.movieland.currencyservice.entity.Currency;
import com.movieland.currencyservice.entity.CurrencyType;

public interface ExternalCurrencyService {
    Currency getCurrency(CurrencyType currencyType);
}
