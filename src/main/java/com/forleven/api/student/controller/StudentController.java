package com.forleven.api.student.controller;

import com.forleven.api.student.entity.Student;
import com.forleven.api.student.dto.StudentDTO;
import com.forleven.api.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping(path = "/{id}")
    public ResponseEntity<Student> findStudentById(
            @PathVariable long id) {
        return studentService.findStudentById(id);
    }

    @GetMapping(path = "/user/{name}")
    public ResponseEntity<List<Student>> findStudentByName(
            @PathVariable String name) {
        return studentService.findStudentByName(name);
    }

    @GetMapping(path = "/status/{status}")
    public ResponseEntity<List<Student>> sortActiveAndInactiveStudents(
            @PathVariable boolean status) {
        return studentService.sortActiveAndInactiveStudents(status);
    }

    @PostMapping
    public Student create(
            @Valid
            @RequestBody StudentDTO studentDTO) {
        return studentService.create(studentDTO);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Student> update(
            @Valid
            @RequestBody StudentDTO studentDTO,
            @PathVariable long id) {
        return studentService.update(id, studentDTO);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Student> delete(
            @PathVariable long id) {
        return studentService.delete(id);
    }

}
