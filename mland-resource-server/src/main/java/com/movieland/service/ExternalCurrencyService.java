package com.movieland.service;

import com.movieland.entity.Currency;
import com.movieland.entity.CurrencyType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mland-currency-service")
public interface ExternalCurrencyService {
    @PostMapping("/currency")
    Currency getCurrency(@RequestBody CurrencyType currencyType);

}
