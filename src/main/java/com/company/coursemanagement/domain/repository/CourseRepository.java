package com.company.coursemanagement.domain.repository;

import com.company.coursemanagement.domain.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course save(Course course);

    Optional<Course> findById(Long id);

    List<Course> findAll();

    boolean existsById(Long id);

    void deleteById(Long id);

    void delete(Course course);

    boolean existsByTitleIgnoreCase(String title);

    List<Course> findByCreditsGreaterThanEqual(Integer credits);

    List<Course> findByTitleContainingIgnoreCase(String title);
}