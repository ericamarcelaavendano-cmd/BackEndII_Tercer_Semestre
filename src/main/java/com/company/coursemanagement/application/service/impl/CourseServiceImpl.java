package com.company.coursemanagement.application.service.impl;

import com.company.coursemanagement.application.dto.CourseDTO;
import com.company.coursemanagement.entity.CourseEntity;
import com.company.coursemanagement.domain.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseDTO> findAll() {
        return courseRepository.findAll().stream()
                .map(c -> new CourseDTO(c.getId(), c.getTitle(), c.getDescription(), c.getCredits()))
                .collect(Collectors.toList());
    }

    public CourseDTO findById(Long id) {
        CourseEntity c = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));
        return new CourseDTO(c.getId(), c.getTitle(), c.getDescription(), c.getCredits());
    }

    public CourseDTO create(CourseDTO dto) {
        CourseEntity entity = new CourseEntity(null, dto.title(), dto.description(), dto.credits());
        CourseEntity saved = courseRepository.save(entity);
        return new CourseDTO(saved.getId(), saved.getTitle(), saved.getDescription(), saved.getCredits());
    }

    public CourseDTO update(Long id, CourseDTO dto) {
        CourseEntity c = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));
        c.setTitle(dto.title());
        c.setDescription(dto.description());
        c.setCredits(dto.credits());
        CourseEntity updated = courseRepository.save(c);
        return new CourseDTO(updated.getId(), updated.getTitle(), updated.getDescription(), updated.getCredits());
    }

    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Curso no encontrado con id: " + id);
        }
        courseRepository.deleteById(id);
    }
}