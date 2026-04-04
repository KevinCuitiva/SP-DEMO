package edu.eci.dosw.segundo_parcial.exception;

import java.time.Instant;

/**
 * Respuesta estandar para errores de la API.
 *
 * Si quieres agregar mas detalle, aqui puedes incluir codigo interno o ruta.
 */
public class ApiErrorResponse {

    private int status;
    private String message;
    private Instant timestamp;

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(int status, String message, Instant timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
