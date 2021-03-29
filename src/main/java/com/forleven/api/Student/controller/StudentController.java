package com.forleven.api.Student.controller;

import com.forleven.api.Student.repository.StudentRepository;
import com.forleven.api.Student.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController{

    private StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List findAll(){
        return studentRepository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id){
        return studentRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Student create(@RequestBody @Valid Student student){
        return studentRepository.save(student);

    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Student> update(@PathVariable("id") long id,
                                 @RequestBody @Valid Student student) {
        return studentRepository.findById(id)
                .map(record -> {
                    record.setName(student.getName());
                    record.setSurname(student.getSurname());
                    record.setEnrollment(student.getEnrollment());
                    record.setPhone(student.getPhone());
                    Student updated = studentRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity <?> delete(@PathVariable long id) {
        return studentRepository.findById(id)
                .map(record -> {
                    studentRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
