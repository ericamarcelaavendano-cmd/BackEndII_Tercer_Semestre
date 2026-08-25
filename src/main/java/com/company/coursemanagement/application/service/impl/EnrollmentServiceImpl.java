package com.company.coursemanagement.application.service.impl;

import com.company.coursemanagement.application.dto.EnrollmentDTO;
import com.company.coursemanagement.application.service.EnrollmentService;
import com.company.coursemanagement.domain.exception.CourseCapacityExceededException;
import com.company.coursemanagement.domain.exception.CourseNotFoundException;
import com.company.coursemanagement.domain.exception.DuplicateEnrollmentException;
import com.company.coursemanagement.domain.exception.EnrollmentNotFoundException;
import com.company.coursemanagement.domain.exception.StudentNotFoundException;
import com.company.coursemanagement.domain.model.Course;
import com.company.coursemanagement.domain.model.Enrollment;
import com.company.coursemanagement.domain.model.EnrollmentStatus;
import com.company.coursemanagement.domain.model.Student;
import com.company.coursemanagement.domain.repository.CourseRepository;
import com.company.coursemanagement.domain.repository.EnrollmentRepository;
import com.company.coursemanagement.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 StudentRepository studentRepository,
                                 CourseRepository courseRepository) {
        this.enrollmentRepository = Objects.requireNonNull(enrollmentRepository);
        this.studentRepository = Objects.requireNonNull(studentRepository);
        this.courseRepository = Objects.requireNonNull(courseRepository);
    }

    @Override
    public EnrollmentDTO create(EnrollmentDTO dto) {
        Student student = studentRepository.findById(dto.studentId())
                .orElseThrow(() -> new StudentNotFoundException(dto.studentId()));

        Course course = courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new CourseNotFoundException(dto.courseId()));

        boolean alreadyEnrolled = !enrollmentRepository
                .findByStudentIdAndCourseIdAndStatus(dto.studentId(), dto.courseId(), EnrollmentStatus.ACTIVE)
                .isEmpty();
        if (alreadyEnrolled) {
            throw new DuplicateEnrollmentException(dto.studentId(), dto.courseId());
        }

        long activeCount = enrollmentRepository
                .findByCourseIdAndStatus(dto.courseId(), EnrollmentStatus.ACTIVE)
                .size();
        if (activeCount >= course.getMaxCapacity()) {
            throw new CourseCapacityExceededException(dto.courseId());
        }

        Enrollment enrollment = new Enrollment(student, course, dto.enrollmentDate(), EnrollmentStatus.ACTIVE);
        Enrollment saved = enrollmentRepository.save(enrollment);
        return toDto(saved);
    }

    @Override
    public EnrollmentDTO findById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
        return toDto(enrollment);
    }

    @Override
    public List<EnrollmentDTO> findAll() {
        return enrollmentRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public EnrollmentDTO cancel(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
        enrollment.cancel();
        Enrollment updated = enrollmentRepository.save(enrollment);
        return toDto(updated);
    }

    @Override
    public void delete(Long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new EnrollmentNotFoundException(id);
        }
        enrollmentRepository.deleteById(id);
    }

    private EnrollmentDTO toDto(Enrollment enrollment) {
        return new EnrollmentDTO(
                enrollment.getId(),
                enrollment.getStudent().getId(),
                enrollment.getCourse().getId(),
                enrollment.getEnrollmentDate(),
                enrollment.getStatus()
        );
    }
}