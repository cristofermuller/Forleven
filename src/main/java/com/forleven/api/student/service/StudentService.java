package com.forleven.api.student.service;

import com.forleven.api.student.dto.StudentDTO;
import com.forleven.api.student.entity.Student;
import com.forleven.api.student.error.ConflictException;
import com.forleven.api.student.error.NotFoundException;
import com.forleven.api.student.repository.StudentRepository;
import com.forleven.api.student.specification.StudentSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private static final String ERRO = "Cadastro não existente";
    private static final String ERROMATRICULA = "Número de matrícula já existe no banco de dados.";

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public ResponseEntity<Student> findStudentById(long id) {
        Optional<Student> studentId = studentRepository.findById(id);
        if (studentId.isEmpty()) {
            throw new NotFoundException(ERRO);
        }

        return studentId
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<Student>> findStudentByName(String name) {
        Specification<Student> spec = StudentSpecification.deNome(name);
        List<Student> students = studentRepository.findAll(spec);
        if (students.isEmpty()) {
            throw new NotFoundException(ERRO);
        }
        return ResponseEntity.ok(students);
    }

    public ResponseEntity<List<Student>> sortActiveAndInactiveStudents(boolean status) {
        Specification<Student> spec = StudentSpecification.deStatus(status);
        List<Student> students = studentRepository.findAll(spec);
        if (students.isEmpty()) {
            throw new NotFoundException(ERRO);
        }
        return ResponseEntity.ok(students);
    }

    public Student create(StudentDTO studentDTO) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEnrollment(studentDTO.getEnrollment());
        if (studentOptional.isPresent()) {
            throw new ConflictException(ERROMATRICULA);
        }

        return studentRepository.save(studentDTO.build());
    }

    public ResponseEntity<Student> update (long id, StudentDTO studentDTO){
        Optional<Student> studentId = studentRepository.findById(id);
        if (studentId.isEmpty()) {
            throw new NotFoundException(ERRO);
        }

        Optional<Student> studentEnrollment = studentRepository
                .findStudentByEnrollment(studentDTO.getEnrollment());
        if (studentEnrollment.isPresent()) {
            throw new ConflictException(ERROMATRICULA);
        }

        return studentId
                .map(record -> {
                    record.setName(studentDTO.getName());
                    record.setSurname(studentDTO.getSurname());
                    record.setEnrollment(studentDTO.getEnrollment());
                    record.setPhones(studentDTO.getPhones());
                    Student updated = studentRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());}


    public ResponseEntity<Student> delete (long id) {
        Optional<Student> studentId = studentRepository.findById(id);
        if (studentId.isEmpty()) {
            throw new NotFoundException(ERRO);
        }

        return studentId
                .map(record -> {
                    record.setStatus(false);
                    Student updated = studentRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

}