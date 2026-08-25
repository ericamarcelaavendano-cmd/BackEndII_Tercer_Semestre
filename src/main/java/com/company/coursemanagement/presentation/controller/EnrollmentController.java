package com.company.coursemanagement.presentation.controller;

import com.company.coursemanagement.application.dto.EnrollmentDTO;
import com.company.coursemanagement.application.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentDTO create(@Valid @RequestBody EnrollmentDTO dto) {
        return enrollmentService.create(dto);
    }

    @GetMapping("/{id}")
    public EnrollmentDTO findById(@PathVariable Long id) {
        return enrollmentService.findById(id);
    }

    @GetMapping
    public List<EnrollmentDTO> findAll() {
        return enrollmentService.findAll();
    }

    @PatchMapping("/{id}/cancel")
    public EnrollmentDTO cancel(@PathVariable Long id) {
        return enrollmentService.cancel(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        enrollmentService.delete(id);
    }
}