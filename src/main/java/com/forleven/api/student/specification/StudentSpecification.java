package com.forleven.api.student.specification;

import com.forleven.api.student.entity.Student;
import com.forleven.api.student.entity.Student_;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    private StudentSpecification() {
        throw new IllegalStateException("Utility Class");
    }

    public static Specification<Student> deId(Long id) {
        return (root, query, builder) ->
                builder.equal(root.get(Student_.id), id);
    }

    public static Specification<Student> deNome(String name) {
        return (root, query, builder) ->
                builder.equal(root.get(Student_.name), name);
    }

    public static Specification<Student> deSobrenome(String surname) {
        return (root, query, builder) ->
                builder.equal(root.get(Student_.surname), surname);
    }

    public static Specification<Student> deMatricula(String enrollment) {
        return (root, query, builder) ->
                builder.equal(root.get(Student_.enrollment), enrollment);
    }

    public static Specification<Student> deTelefone(String phones) {
        return (root, query, builder) ->
                builder.equal(root.get(Student_.phones), phones);
    }

    public static Specification<Student> deStatus(boolean status) {
        return (root, query, builder) ->
                builder.equal(root.get(Student_.status), status);
    }
}

