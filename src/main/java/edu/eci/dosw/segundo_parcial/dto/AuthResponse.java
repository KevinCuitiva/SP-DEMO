package edu.eci.dosw.segundo_parcial.dto;

/**
 * Respuesta que devuelve el login exitoso.
 *
 * Si despues quieres agregar mas datos, este DTO es el que deberias ampliar.
 */
public class AuthResponse {

    private String token;
    private String tokenType = "Bearer";
    private UserResponse user;

    public AuthResponse() {
    }

    // Crea la respuesta con token y datos del usuario autenticado.
    public AuthResponse(String token, UserResponse user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
