package com.company.coursemanagement.application.service.impl;

import com.company.coursemanagement.application.dto.EnrollmentDTO;
import com.company.coursemanagement.application.service.EnrollmentService;
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

import java.time.LocalDate;
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

        enrollmentRepository.findByStudentIdAndCourseIdAndStatus(
                        dto.studentId(), dto.courseId(), EnrollmentStatus.ACTIVE)
                .ifPresent(e -> {
                    throw new DuplicateEnrollmentException(dto.studentId(), dto.courseId());
                });

        LocalDate enrollmentDate = dto.enrollmentDate() != null ? dto.enrollmentDate() : LocalDate.now();
        Enrollment enrollment = new Enrollment(student, course, enrollmentDate, EnrollmentStatus.ACTIVE);
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
    public List<EnrollmentDTO> findByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<EnrollmentDTO> findByCourse(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<EnrollmentDTO> findByStatus(EnrollmentStatus status) {
        return enrollmentRepository.findByStatus(status).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public long countActiveByCourse(Long courseId) {
        return enrollmentRepository.countByCourseIdAndStatus(courseId, EnrollmentStatus.ACTIVE);
    }

    @Override
    public EnrollmentDTO cancel(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
        enrollment.cancel();
        return toDto(enrollmentRepository.save(enrollment));
    }

    @Override
    public EnrollmentDTO complete(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
        enrollment.complete();
        return toDto(enrollmentRepository.save(enrollment));
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