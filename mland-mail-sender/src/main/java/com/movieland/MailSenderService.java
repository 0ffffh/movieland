package com.movieland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MailSenderService {

    public static void main(String[] args) {
        SpringApplication.run(MailSenderService.class, args);
    }

}
