package com.company.coursemanagement.domain.repository;

import com.company.coursemanagement.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.company.coursemanagement.domain.repository.CourseRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
}