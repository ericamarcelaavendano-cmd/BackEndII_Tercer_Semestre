package com.company.coursemanagement.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Setter
    @Column(nullable = false)
    private LocalDate enrollmentDate;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status;

    protected Enrollment() {
        // Constructor vacío requerido por JPA/Hibernate
    }

    public Enrollment(Student student, Course course, LocalDate enrollmentDate, EnrollmentStatus status) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate != null ? enrollmentDate : LocalDate.now();
        this.status = status != null ? status : EnrollmentStatus.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void cancel() {
        this.status = EnrollmentStatus.CANCELLED;
    }

    public void complete() {
        this.status = EnrollmentStatus.COMPLETED;
    }
}