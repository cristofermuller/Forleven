package com.forleven.api.Student.service;

import com.forleven.api.Student.Student;
import com.forleven.api.Student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List findAll() {
        return studentRepository.findAll();
    }

    public ResponseEntity<Student> findById(long id) {
        Optional<Student> studentId = studentRepository
                .findById(id);
        if (studentId.isEmpty()) {
            throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND, "Cadastro não existente");
        }

        return studentId
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    public Student create(Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEnrollment(student.getEnrollment());
        if (studentOptional.isPresent()) {
            throw new ResponseStatusException
                    (HttpStatus.CONFLICT, "Número de matrícula já existe no banco de dados.");
        }
        return studentRepository.save(student);
    }

    public ResponseEntity<Student> update (long id, Student student){
        Optional<Student> studentId = studentRepository
                .findById(id);
        if (studentId.isEmpty()) {
            throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND, "Cadastro não existente");
        }

        Optional<Student> studentEnrollment = studentRepository
                .findStudentByEnrollment(student.getEnrollment());
        if (studentEnrollment.isPresent()) {
            throw new ResponseStatusException
                    (HttpStatus.CONFLICT, "Número de matrícula já existe no banco de dados.");
        }

        return studentId
                .map(record -> {
                    record.setName(student.getName());
                    record.setSurname(student.getSurname());
                    record.setEnrollment(student.getEnrollment());
                    record.setPhone(student.getPhone());
                    Student updated = studentRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());}


    public ResponseEntity<?> delete (long id) {
        Optional<Student> studentId = studentRepository.findById(id);
        if (studentId.isEmpty()) {
            throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND, "Cadastro não existente");
        }

        return studentId
                .map(record -> {
                    studentRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}