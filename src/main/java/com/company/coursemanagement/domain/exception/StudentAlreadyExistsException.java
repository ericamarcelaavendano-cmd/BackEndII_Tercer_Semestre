package com.company.coursemanagement.domain.exception;

public class StudentAlreadyExistsException extends RuntimeException {
    public StudentAlreadyExistsException(String email) {
        super("Ya existe un estudiante registrado con el correo: " + email);
    }
}