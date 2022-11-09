package com.seminario.store.service;

import com.seminario.store.dto.Auth;
import commons.rest.models.User;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final JwtEncoder jwtEncoder;
    private final WebClient.Builder client;

    public TokenService(JwtEncoder jwtEncoder, WebClient.Builder client) {
        this.jwtEncoder = jwtEncoder;
        this.client = client;
    }

    public Mono<Auth> generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        var user = client.build().get()
                .uri("http://user-management/users/by-email/%s".formatted(authentication.getName()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class)
                .map(u -> new Auth(
                        jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(),
                        u.getId(),
                        u.getEmail(),
                        u.getRole().getName()));

        return user;
    }
}
