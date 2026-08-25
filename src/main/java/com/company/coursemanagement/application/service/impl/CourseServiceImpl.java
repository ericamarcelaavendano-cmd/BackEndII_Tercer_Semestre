package com.company.coursemanagement.application.service.impl;

import com.company.coursemanagement.application.dto.CourseDTO;
import com.company.coursemanagement.application.service.CourseService;
import com.company.coursemanagement.domain.exception.CourseNotFoundException;
import com.company.coursemanagement.domain.model.Course;
import com.company.coursemanagement.domain.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = Objects.requireNonNull(courseRepository);
    }

    @Override
    public CourseDTO create(CourseDTO dto) {
        Course course = new Course(dto.code(), dto.name(), dto.description(), dto.maxCapacity());
        Course saved = courseRepository.save(course);
        return toDto(saved);
    }

    @Override
    public CourseDTO findById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        return toDto(course);
    }

    @Override
    public List<CourseDTO> findAll() {
        return courseRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public CourseDTO update(Long id, CourseDTO dto) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        existing.setCode(dto.code());
        existing.setName(dto.name());
        existing.setDescription(dto.description());
        existing.setMaxCapacity(dto.maxCapacity());

        Course updated = courseRepository.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }

    private CourseDTO toDto(Course course) {
        return new CourseDTO(
                course.getId(),
                course.getCode(),
                course.getName(),
                course.getDescription(),
                course.getMaxCapacity()
        );
    }
}