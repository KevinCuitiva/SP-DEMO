package edu.eci.dosw.segundo_parcial.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.eci.dosw.segundo_parcial.entity.Role;
import edu.eci.dosw.segundo_parcial.entity.UserEntity;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService(
            "second-parcial-dev-secret-key-change-me-second-parcial-dev-secret-key-change-me", 86400000L);

    @Test
    void tokenCanBeCreatedAndRead() {
        UserEntity user = new UserEntity("Admin", "admin@example.com", "encoded", Role.ADMIN);

        String token = jwtService.generateToken(user);

        assertEquals("admin@example.com", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, "admin@example.com"));
    }
}
