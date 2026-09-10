package com.company.coursemanagement.application.service.impl;

import com.company.coursemanagement.application.dto.CourseDTO;
import com.company.coursemanagement.application.service.CourseService;
import com.company.coursemanagement.domain.exception.CourseAlreadyExistsException;
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
        if (courseRepository.existsByTitleIgnoreCase(dto.title())) {
            throw new CourseAlreadyExistsException(dto.title());
        }

        Course course = new Course(dto.title(), dto.description(), dto.credits());
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
    public List<CourseDTO> search(String query) {
        String term = query == null ? "" : query.trim();
        return courseRepository.findByTitleContainingIgnoreCase(term).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<CourseDTO> findByMinCredits(Integer credits) {
        return courseRepository.findByCreditsGreaterThanEqual(credits).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public CourseDTO update(Long id, CourseDTO dto) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        existing.setTitle(dto.title());
        existing.setDescription(dto.description());
        existing.setCredits(dto.credits());

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
                course.getTitle(),
                course.getDescription(),
                course.getCredits()
        );
    }
}