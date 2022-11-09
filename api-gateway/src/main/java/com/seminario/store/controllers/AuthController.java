package com.seminario.store.controllers;

import com.seminario.store.dto.Auth;
import com.seminario.store.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public ResponseEntity<Mono<Auth>> token(Authentication authentication) {
        LOGGER.info("Token request for user: {}", authentication.getName());

        Mono<Auth> token = tokenService.generateToken(authentication);
        LOGGER.debug("Token granted: {}", token);
        return ResponseEntity.ok(token);
    }
}
