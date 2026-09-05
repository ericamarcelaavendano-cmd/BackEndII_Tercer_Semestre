package com.company.coursemanagement.application.service.impl;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class StudentAlreadyExistsException extends Throwable {
    public StudentAlreadyExistsException(@NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email) {
    }
}
