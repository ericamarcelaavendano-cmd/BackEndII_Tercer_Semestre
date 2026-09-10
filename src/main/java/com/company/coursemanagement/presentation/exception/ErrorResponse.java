package com.company.coursemanagement.presentation.exception;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Forma única de respuesta de error para TODOS los controladores
 * (Student, Course, Enrollment). Así el frontend solo necesita
 * manejar un solo formato de error en toda la API.
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        List<String> details
) {
    public ErrorResponse(int status, String error, String message) {
        this(LocalDateTime.now(), status, error, message, List.of());
    }

    public ErrorResponse(int status, String error, String message, List<String> details) {
        this(LocalDateTime.now(), status, error, message, details);
    }
}
