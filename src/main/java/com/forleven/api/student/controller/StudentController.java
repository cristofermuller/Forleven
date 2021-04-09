package com.forleven.api.student.controller;

import com.forleven.api.student.Student;
import com.forleven.api.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController{

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> findAllStudents() {
        return studentService.findAllStudents();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Student> findStudentById(
            @PathVariable long id) {
        return studentService.findStudentById(id);
    }

    @PostMapping
    public Student create(
            @Valid
            @RequestBody Student student) {
        return studentService.create(student);

    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Student> update(
            @Valid
            @RequestBody Student student,
            @PathVariable long id) {
        return studentService.update(id, student);
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity <Object> delete(
            @PathVariable long id) {
        return studentService.delete(id);
    }

}
