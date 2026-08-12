package com.company.coursemanagement.domain.exception;

public class CourseCapacityExceededException extends BusinessException {

    public CourseCapacityExceededException(Long courseId) {
        super("Course has reached its maximum capacity: " + courseId);
    }
}