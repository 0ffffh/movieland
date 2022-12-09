package com.movieland.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("jwt")
@Getter
@Setter
public class JWTProperties {
    @NestedConfigurationProperty
    private String secret;
    @NestedConfigurationProperty
    private long accessTokenExpires;
    @NestedConfigurationProperty
    private long refreshTokenExpires;
}
