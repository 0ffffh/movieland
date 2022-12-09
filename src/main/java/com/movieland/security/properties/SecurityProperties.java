package com.movieland.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("security")
@Getter
@Setter
public class SecurityProperties {


    @NestedConfigurationProperty
    private boolean enabled;

}
