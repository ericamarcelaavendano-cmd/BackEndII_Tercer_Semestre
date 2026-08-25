package com.company.coursemanagement.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CourseDTO(
        Long id,

        @NotBlank(message = "Code is required")
        String code,

        @NotBlank(message = "Name is required")
        String name,

        String description,

        @NotNull(message = "Max capacity is required")
        @Positive(message = "Max capacity must be greater than 0")
        Integer maxCapacity
) {
}