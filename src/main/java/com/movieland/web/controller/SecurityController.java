package com.movieland.web.controller;

import com.movieland.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class SecurityController {
    private final SecurityService securityService;

    @GetMapping("/refresh")
    public void refreshTokens(HttpServletRequest request, HttpServletResponse response) {
        securityService.getAuthenticationFromRefreshToken(request, response);
    }

}
