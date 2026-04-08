package edu.eci.dosw.segundo_parcial.security;

import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import edu.eci.dosw.segundo_parcial.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

class JwtAuthenticationFilterTest {

    // Verifica que, si no hay token, la peticion sigue normal.
    @Test
    void passesThroughWithoutAuthorizationHeader() throws ServletException, IOException {
        JwtService jwtService = Mockito.mock(JwtService.class);
        UserDetailsService userDetailsService = Mockito.mock(UserDetailsService.class);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userDetailsService);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = Mockito.mock(FilterChain.class);

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
    }
}
