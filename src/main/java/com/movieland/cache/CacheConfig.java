package com.movieland.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.management.timer.Timer;

@Slf4j
@EnableCaching
@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class CacheConfig {
    private static final String GENRE_CACHE = "genres";
    private static final String COUNTRY_CACHE = "countries";
    private static final String CURRENCY_CACHE = "currency";
    private static final String MOVIE_CACHE = "movies";
    private static final long FOUR_HOURS = Timer.ONE_HOUR * 4;

    @Scheduled(fixedRate = FOUR_HOURS)
    @CacheEvict(value = MOVIE_CACHE, allEntries = true)
    public void clearMovieCache() {
        log.info("Reset movie cache");
    }

    @Scheduled(fixedRate = FOUR_HOURS)
    @CacheEvict(value = GENRE_CACHE, allEntries = true)
    public void clearGenresCache() {
        log.info("Reset genres cache");
    }

    @Scheduled(fixedRate = FOUR_HOURS)
    @CacheEvict(value = COUNTRY_CACHE, allEntries = true)
    public void clearCountriesCache() {
        log.info("Reset countries cache");
    }

    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict(value = CURRENCY_CACHE, allEntries = true)
    public void enrichCurrencyCache() {
        log.info("Reset currency cache");
    }
}
