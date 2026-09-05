package com.company.coursemanagement.presentationExcepcion;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        LocalDateTime timestamp
) {}