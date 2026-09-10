package com.company.coursemanagement.application.service.impl;

import com.company.coursemanagement.application.dto.StudentDTO;
import com.company.coursemanagement.application.service.StudentService;
import com.company.coursemanagement.domain.exception.StudentAlreadyExistsException;
import com.company.coursemanagement.domain.exception.StudentNotFoundException;
import com.company.coursemanagement.domain.model.Student;
import com.company.coursemanagement.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = Objects.requireNonNull(studentRepository);
    }

    @Override
    public StudentDTO create(StudentDTO dto) {
        if (studentRepository.existsByEmail(dto.email())) {
            throw new StudentAlreadyExistsException(dto.email());
        }

        Student student = new Student(dto.firstName(), dto.lastName(), dto.email(), dto.birthDate());
        Student saved = studentRepository.save(student);
        return toDto(saved);
    }

    @Override
    public StudentDTO findById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        return toDto(student);
    }

    @Override
    public List<StudentDTO> findAll() {
        return studentRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<StudentDTO> search(String query) {
        String term = query == null ? "" : query.trim();
        return studentRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        term, term, term)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<StudentDTO> findAllSortedByLastName() {
        return studentRepository.findAllByOrderByLastNameAsc().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<StudentDTO> findBornBefore(LocalDate date) {
        return studentRepository.findByBirthDateBefore(date).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public StudentDTO update(Long id, StudentDTO dto) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        studentRepository.findByEmail(dto.email()).ifPresent(student -> {
            if (!student.getId().equals(id)) {
                throw new StudentAlreadyExistsException(dto.email());
            }
        });

        existing.setFirstName(dto.firstName());
        existing.setLastName(dto.lastName());
        existing.setEmail(dto.email());
        existing.setBirthDate(dto.birthDate());

        Student updated = studentRepository.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }

    private StudentDTO toDto(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getBirthDate()
        );
    }
}