package com.movieland.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.movieland.entity.Role;
import com.movieland.entity.User;
import com.movieland.security.properties.JWTProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class TokenManager {
    private static final String ROLES = "roles";
    private final JWTProperties jwtProperties;

    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public String createRefreshToken(Authentication authentication) {
        log.info("Create Refresh token for user : {}", authentication.getName());
        User user = (User) authentication.getPrincipal();
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpires()))
                .sign(algorithm);
    }

    public String createAccessToken(Authentication authentication) {
        log.info("Create Access token for user : {}", authentication.getName());
        return JWT.create()
                .withSubject(authentication.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpires()))
                .withClaim(ROLES, authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .sign(algorithm);
    }

    public UsernamePasswordAuthenticationToken getAuthenticationFromToken(String token) {
        DecodedJWT claims = verifier.verify(token);
        String username = claims.getSubject();

        List<Role> roleList = claims.getClaim(ROLES).asList(Role.class);

        return new UsernamePasswordAuthenticationToken(username, null, roleList);
    }
}
