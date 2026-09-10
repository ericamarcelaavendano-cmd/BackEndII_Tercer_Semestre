package com.company.coursemanagement.application.service;

import com.company.coursemanagement.application.dto.EnrollmentDTO;
import com.company.coursemanagement.domain.model.EnrollmentStatus;

import java.util.List;

public interface EnrollmentService {

    EnrollmentDTO create(EnrollmentDTO dto);

    EnrollmentDTO findById(Long id);

    List<EnrollmentDTO> findAll();

    List<EnrollmentDTO> findByStudent(Long studentId);

    List<EnrollmentDTO> findByCourse(Long courseId);

    List<EnrollmentDTO> findByStatus(EnrollmentStatus status);

    long countActiveByCourse(Long courseId);

    EnrollmentDTO cancel(Long id);

    EnrollmentDTO complete(Long id);

    void delete(Long id);
}