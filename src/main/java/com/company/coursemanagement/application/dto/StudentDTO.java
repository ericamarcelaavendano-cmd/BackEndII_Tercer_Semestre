package com.company.coursemanagement.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record StudentDTO(
        Long id,

        @NotBlank(message = "El nombre no puede estar vacío")
        String firstName,

        @NotBlank(message = "El apellido no puede estar vacío")
        String lastName,

        @NotBlank(message = "El correo no puede estar vacío")
        @Email(message = "Debe proporcionar un formato de correo válido")
        String email,

        @NotNull(message = "La fecha de nacimiento es obligatoria")
        LocalDate birthDate
) {}