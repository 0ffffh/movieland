package com.movieland.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieland.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService {
    private static final String REFRESH_TOKEN = "refresh_token";
    private final UserDetailsService userService;
    private final TokenManager tokenManager;
    private final ObjectMapper mapper = new ObjectMapper();


    public void authenticate(Authentication authentication, HttpServletResponse response) {
        String accessToken = tokenManager.createAccessToken(authentication);
        String refreshToken = tokenManager.createRefreshToken(authentication);

        sendUserResponse(response, LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .nickname(authentication.getName())
                .build());
    }

    public UsernamePasswordAuthenticationToken getAuthenticationFromAccessToken(String token) {
        return tokenManager.getAuthenticationFromToken(token);
    }

    @SneakyThrows
    public void getAuthenticationFromRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = request.getHeader(REFRESH_TOKEN);
        UsernamePasswordAuthenticationToken authenticationToken
                = tokenManager.getAuthenticationFromToken(refreshToken);
        User user = (User) userService.loadUserByUsername(authenticationToken.getName());
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authenticate(authentication, response);
    }

    @SneakyThrows
    private void sendUserResponse(HttpServletResponse response, LoginResponse loginResponse) {
        log.info("User {} authenticated", loginResponse.getNickname());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(), loginResponse);
    }

}
