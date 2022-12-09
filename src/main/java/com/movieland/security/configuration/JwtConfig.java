package com.movieland.security.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.movieland.security.properties.JWTProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {
    private final JWTProperties jwtProperties;

    @Bean
    public Algorithm jwtAlgorithm() {
        return Algorithm.HMAC256(jwtProperties.getSecret().getBytes());
    }

    @Bean
    public JWTVerifier jwtVerifier() {
        return JWT.require(jwtAlgorithm()).build();
    }
}
