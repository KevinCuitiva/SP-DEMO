package edu.eci.dosw.segundo_parcial.exception;

/**
 * Se lanza cuando un recurso solicitado no existe.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
