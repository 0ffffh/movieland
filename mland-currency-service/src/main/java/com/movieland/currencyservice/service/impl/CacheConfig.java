package com.movieland.currencyservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableCaching
@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class CacheConfig {

    private static final String CURRENCY_CACHE = "currency";

    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict(value = CURRENCY_CACHE, allEntries = true)
    public void enrichCurrencyCache() {
        log.info("Reset currency cache");
    }
}
