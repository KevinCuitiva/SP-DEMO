package edu.eci.dosw.segundo_parcial.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Crea y valida tokens JWT.
 *
 * Si cambias la firma o el tiempo de expiracion, tambien debes actualizar los tests.
 */
@Service
public class JwtService {

    private final String secret;
    private final long expirationMillis;

    public JwtService(@Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expirationMillis) {
        this.secret = secret;
        this.expirationMillis = expirationMillis;
    }

    // Genera un token con un sujeto y claims adicionales.
    public String generateToken(String subject, Map<String, Object> extraClaims) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .claims(extraClaims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(signingKey())
                .compact();
    }

    // Genera un token usando el correo del usuario y su rol.
    public String generateToken(edu.eci.dosw.segundo_parcial.entity.UserEntity user) {
        return generateToken(user.getEmail(), Map.of("role", user.getRole().name()));
    }

    // Extrae el correo que viene como sujeto en el token.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Verifica que el token corresponda al usuario esperado y que no haya vencido.
    public boolean isTokenValid(String token, String expectedUsername) {
        return expectedUsername != null
                && expectedUsername.equalsIgnoreCase(extractUsername(token))
                && extractExpiration(token).after(new Date());
    }

    // Lee la fecha de expiracion del token.
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extrae cualquier claim del token.
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }

    // Crea la llave de firma a partir del secreto configurado.
    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
