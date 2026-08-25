package com.company.coursemanagement.application.dto;

import com.company.coursemanagement.domain.model.EnrollmentStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EnrollmentDTO(
        Long id,

        @NotNull(message = "Student id is required")
        Long studentId,

        @NotNull(message = "Course id is required")
        Long courseId,

        LocalDate enrollmentDate,

        EnrollmentStatus status
) {
}