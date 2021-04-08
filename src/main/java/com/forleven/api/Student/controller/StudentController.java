package com.forleven.api.Student.controller;

import com.forleven.api.Student.Student;
import com.forleven.api.Student.service.StudentService;
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
    public List findAll() {
        return studentService.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(
            @PathVariable long id) {
        return studentService.findById(id);
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
    public ResponseEntity <?> delete(
            @PathVariable long id) {
        return studentService.delete(id);
    }

}
