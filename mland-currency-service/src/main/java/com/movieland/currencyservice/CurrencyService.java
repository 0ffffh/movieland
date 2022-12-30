package com.movieland.currencyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CurrencyService {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyService.class, args);
    }

}
