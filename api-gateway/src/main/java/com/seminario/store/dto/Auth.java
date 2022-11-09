package com.seminario.store.dto;

public record Auth(String token, Long userId, String user, String role) {}
