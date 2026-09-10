package com.company.coursemanagement.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseDTO(
        Long id,

        @NotBlank(message = "El título del curso no puede estar vacío")
        String title,

        String description,

        @NotNull(message = "Los créditos son obligatorios")
        @Min(value = 1, message = "El curso debe tener al menos 1 crédito")
        Integer credits
) {
}
