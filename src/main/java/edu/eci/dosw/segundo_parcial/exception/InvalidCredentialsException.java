package edu.eci.dosw.segundo_parcial.exception;

/**
 * Se lanza cuando el login no coincide con un usuario valido.
 */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
