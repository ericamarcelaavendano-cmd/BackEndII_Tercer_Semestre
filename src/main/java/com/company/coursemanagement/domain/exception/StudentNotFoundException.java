package com.company.coursemanagement.domain.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super("No se encontró el estudiante con el ID: " + id);
    }
}