package com.company.coursemanagement.application.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record EnrollmentDTO(
        Long id,

        @NotNull(message = "El ID del estudiante es obligatorio")
        Long studentId,

        @NotNull(message = "El ID del curso es obligatorio")
        Long courseId,

        LocalDate enrollmentDate,
        com.company.coursemanagement.domain.model.EnrollmentStatus status) {}