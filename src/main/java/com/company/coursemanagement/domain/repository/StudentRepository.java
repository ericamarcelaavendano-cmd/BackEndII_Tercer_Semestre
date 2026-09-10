package com.company.coursemanagement.domain.repository;

import com.company.coursemanagement.domain.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Aunque JpaRepository ya provee estos métodos por herencia,
 * los declaramos explícitamente aquí para dejar clara la
 * intención del Repository Pattern: qué operaciones expone
 * este repositorio sobre Student.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // --- CRUD explícito ---
    Student save(Student student);

    Optional<Student> findById(Long id);

    List<Student> findAll();

    boolean existsById(Long id);

    void deleteById(Long id);

    void delete(Student student);

    // --- Consultas propias del negocio ---
    List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String firstName, String lastName, String email);

    Optional<Student> findByEmail(String email);
    boolean existsByEmail(String email);

    List<Student> findAllByOrderByLastNameAsc();

    List<Student> findByBirthDateBefore(LocalDate date);
}

