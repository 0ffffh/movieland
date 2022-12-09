package com.movieland.service;

import com.movieland.entity.Currency;

import java.util.List;

public interface ExternalCurrencyService {

    List<Currency> getCurrencyRate();

}
