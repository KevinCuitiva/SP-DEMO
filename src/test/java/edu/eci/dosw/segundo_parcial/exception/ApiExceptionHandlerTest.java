package edu.eci.dosw.segundo_parcial.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ApiExceptionHandlerTest {

    private final ApiExceptionHandler handler = new ApiExceptionHandler();

    @Test
    void notFoundMapsTo404() {
        var response = handler.handleNotFound(new ResourceNotFoundException("No existe"));

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
    }
}
