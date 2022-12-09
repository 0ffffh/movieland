package com.movieland.security.configuration;

import com.movieland.security.SecurityService;
import com.movieland.security.filter.AuthorizationJWTFilter;
import com.movieland.security.filter.UserPasswordAuthenticationFilter;
import com.movieland.security.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
        @RequiredArgsConstructor
public class SecurityConfig {
    private final SecurityProperties securityProperties;
    private final SecurityService securityService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserPasswordAuthenticationFilter userPasswordAuthenticationFilter(AuthenticationConfiguration authConfig) throws Exception {
        UserPasswordAuthenticationFilter userPasswordAuthenticationFilter =
                new UserPasswordAuthenticationFilter();
        userPasswordAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        userPasswordAuthenticationFilter.setAuthenticationManager(authenticationManager(authConfig));
        userPasswordAuthenticationFilter.setUsernameParameter("email");
        userPasswordAuthenticationFilter.setPasswordParameter("password");
        return userPasswordAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {
        if (securityProperties.isEnabled()) {
            return http
                    .csrf().disable()
                    .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                    .authorizeRequests(auth -> auth
                            .antMatchers(GET, "/api/v1/movie/**").permitAll()
                            .antMatchers(POST, "/api/v1/login/**").permitAll()
                            .antMatchers(GET, "/api/v1/refresh/**").permitAll()
                            .anyRequest().authenticated())
                    .addFilter(userPasswordAuthenticationFilter(authConfig))
                    .addFilterBefore(new AuthorizationJWTFilter(securityService), UsernamePasswordAuthenticationFilter.class)
                    .build();
        } else {
            return http
                    .csrf().disable()
                    .authorizeRequests(auth -> auth.anyRequest().permitAll())
                    .build();
        }
    }

}
