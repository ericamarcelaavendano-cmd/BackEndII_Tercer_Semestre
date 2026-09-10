package com.company.coursemanagement.domain.exception;

public class CourseAlreadyExistsException extends BusinessException {
    public CourseAlreadyExistsException(String title) {
        super("Ya existe un curso registrado con el título: " + title);
    }
}