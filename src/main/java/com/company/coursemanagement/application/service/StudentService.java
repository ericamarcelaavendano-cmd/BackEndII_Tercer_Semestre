package com.company.coursemanagement.application.service;

import com.company.coursemanagement.application.dto.StudentDTO;

import java.util.List;

public interface StudentService {

    StudentDTO create(StudentDTO dto);

    StudentDTO findById(Long id);

    List<StudentDTO> findAll();

    List<StudentDTO> search(String query);

    StudentDTO update(Long id, StudentDTO dto);

    void delete(Long id);
}