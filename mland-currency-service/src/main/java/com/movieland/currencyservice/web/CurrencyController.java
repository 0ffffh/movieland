package com.movieland.currencyservice.web;

import com.movieland.currencyservice.entity.Currency;
import com.movieland.currencyservice.entity.CurrencyType;
import com.movieland.currencyservice.service.ExternalCurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/currency")
public class CurrencyController {

    private final ExternalCurrencyService externalCurrencyService;

    @PostMapping()
    Currency get(@RequestBody CurrencyType currencyType) {
        return externalCurrencyService.getCurrency(currencyType);
    }

}
