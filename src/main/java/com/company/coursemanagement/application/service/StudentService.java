package com.company.coursemanagement.application.service;

import com.company.coursemanagement.application.dto.StudentDTO;

import java.time.LocalDate;
import java.util.List;

public interface StudentService {

    StudentDTO create(StudentDTO dto);

    StudentDTO findById(Long id);

    List<StudentDTO> findAll();

    List<StudentDTO> search(String query);

    List<StudentDTO> findAllSortedByLastName();

    List<StudentDTO> findBornBefore(LocalDate date);

    StudentDTO update(Long id, StudentDTO dto);

    void delete(Long id);
}