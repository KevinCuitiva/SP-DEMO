package edu.eci.dosw.segundo_parcial.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.eci.dosw.segundo_parcial.security.JwtAuthenticationFilter;
import org.springframework.security.crypto.password.PasswordEncoder;

class SecurityConfigTest {

    @Test
    void passwordEncoderBeanIsAvailable() {
        SecurityConfig config = new SecurityConfig(Mockito.mock(JwtAuthenticationFilter.class));
        PasswordEncoder encoder = config.passwordEncoder();
        assertNotNull(encoder.encode("test"));
    }
}
