package com.movieland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ResourceServer {

	public static void main(String[] args) {
		SpringApplication.run(ResourceServer.class, args);
	}

}
