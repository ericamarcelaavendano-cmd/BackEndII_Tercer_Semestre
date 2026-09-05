package com.company.coursemanagement.application.service;

import com.company.coursemanagement.application.dto.EnrollmentDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface EnrollmentService {

    EnrollmentDTO create(EnrollmentDTO dto);

    EnrollmentDTO findById(Long id);

    List<EnrollmentDTO> findAll();

    EnrollmentDTO cancel(Long id);

    void delete(Long id);

    void unenroll(Long id);

    EnrollmentDTO enrollStudent(@Valid EnrollmentDTO dto);
}