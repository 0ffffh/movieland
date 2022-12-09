package com.movieland.security.filter;

import com.movieland.exception.SecurityException;
import com.movieland.security.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationJWTFilter extends OncePerRequestFilter {
    private static final String ACCESS_TOKEN = "access_token";

    private final SecurityService securityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String access_token = request.getHeader(ACCESS_TOKEN);
        if (access_token != null) {
            try {
                UsernamePasswordAuthenticationToken authentication
                        = securityService.getAuthenticationFromAccessToken(access_token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                throw new SecurityException(e.getMessage());
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
