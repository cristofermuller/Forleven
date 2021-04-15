package com.forleven.api;

import com.forleven.api.student.dto.StudentDTO;
import com.forleven.api.student.entity.Student;
import com.forleven.api.student.error.ConflictException;
import com.forleven.api.student.error.NotFoundException;
import com.forleven.api.student.repository.StudentRepository;
import com.forleven.api.student.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class StudentServiceTest {

	private StudentRepository studentRepository;

	private StudentService studentService;

	@BeforeEach
	void setUp() {

		studentRepository = Mockito.mock(StudentRepository.class);
		studentService = new StudentService(studentRepository);
	}

	@Test
	void findAllSuccess() {

		Student student = new Student
				(1L, "nome", "sobrenome", "matricula", null);
		ArgumentCaptor<Student> studentArgumentCaptor =
				ArgumentCaptor.forClass(Student.class);

		studentRepository.save(student);

		when(studentRepository.save(any())).thenReturn(student);

		studentService.findAllStudents();

		verify(studentRepository, times(1)).findAll();
		verify(studentRepository, times(1)).save(studentArgumentCaptor.capture());

		Student capturedStudent = studentArgumentCaptor.getValue();

		assertEquals(student, capturedStudent);
	}

	@Test
	void findByIdSuccess() {
		Student student = new Student
				(1L, "nome", "sobrenome", "matricula", null);
		ArgumentCaptor<Student> studentArgumentCaptor =
				ArgumentCaptor.forClass(Student.class);

		studentRepository.save(student);

		when(studentRepository.save(any())).thenReturn(student);
		when(studentRepository.findById(any())).thenReturn(Optional.of(student));

		studentService.findStudentById(1L);

		verify(studentRepository, times(1)).save(studentArgumentCaptor.capture());
		verify(studentRepository, times(1)).findById(1L);

		Student capturedStudent = studentArgumentCaptor.getValue();

		assertEquals(student, capturedStudent);
	}

	@Test
	void findByIdFail() {
		Student student = new Student
				(1L, "nome", "sobrenome", "matricula", null);

		when(studentRepository.findById(2L)).thenReturn(Optional.of(student));

		Exception exception = assertThrows(NotFoundException.class, () ->
				studentService.findStudentById(1L));

		verify(studentRepository, times(1)).findById(1L);

		String expectedMessage = "Cadastro não existente";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}


	@Test
	void createStudentSuccess() {
		Student student = new Student
				(1L, "nome", "sobrenome", "matricula", null);
		StudentDTO studentDTO = new StudentDTO
				(2L, "nome", "sobrenome", "matricula", null);
		ArgumentCaptor<Student> studentArgumentCaptor =
				ArgumentCaptor.forClass(Student.class);

		when(studentRepository.save(any())).thenReturn(student);

		studentService.create(studentDTO);

		verify(studentRepository, times(1)).save(studentArgumentCaptor.capture());
		verify(studentRepository, times(1)).findStudentByEnrollment(any());

		Student capturedStudent = studentArgumentCaptor.getValue();

		assertEquals(student.getName(), capturedStudent.getName());
		assertEquals(student.getSurname(), capturedStudent.getSurname());
		assertEquals(student.getEnrollment(), capturedStudent.getEnrollment());
	}

	@Test()
	void createStudentDuplicatedEnrollmentTest() {
		StudentDTO studentDTO = new StudentDTO
				(1L, "nome", "sobrenome", "matricula", null);

		when(studentRepository.findStudentByEnrollment("matricula")).thenReturn(Optional.of(studentDTO.build()));

		Exception exception = assertThrows(ConflictException.class, () -> studentService.create(studentDTO));

		verify(studentRepository, times(1)).findStudentByEnrollment("matricula");
		verify(studentRepository, times(0)).save(any());

		String expectedMessage = "Número de matrícula já existe no banco de dados.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void updateStudentSuccess() {
		Student student = new Student
				(1L, "nome", "sobrenome", "matricula", null);
		StudentDTO studentDTO = new StudentDTO
				(2L, "nome", "sobrenome", "matricula", null);
		ArgumentCaptor<Student> studentArgumentCaptor =
				ArgumentCaptor.forClass(Student.class);

		when(studentRepository.save(any())).thenReturn(student);
		when(studentRepository.findById(any())).thenReturn(Optional.of(student));

		studentService.update(1L, studentDTO);

		verify(studentRepository, times(1)).findById(1L);
		verify(studentRepository, times(1)).findStudentByEnrollment(any());
		verify(studentRepository, times(1)).save(studentArgumentCaptor.capture());

		Student capturedStudent = studentArgumentCaptor.getValue();

		assertEquals(student.getName(), capturedStudent.getName());
		assertEquals(student.getSurname(), capturedStudent.getSurname());
		assertEquals(student.getEnrollment(), capturedStudent.getEnrollment());
	}

	@Test
	void updateStudentDuplicatedEnrollmentTest() {
		StudentDTO studentDTO = new StudentDTO
				(1L, "nome", "sobrenome", "matricula", null);

		when(studentRepository.findStudentByEnrollment("matricula")).thenReturn(Optional.of(studentDTO.build()));
		when(studentRepository.findById(1L)).thenReturn(Optional.of(studentDTO.build()));

		Exception exception = assertThrows(ConflictException.class, () -> studentService.update(1L, studentDTO));

		verify(studentRepository, times(1)).findById(1L);
		verify(studentRepository, times(1)).findStudentByEnrollment("matricula");
		verify(studentRepository, times(0)).save(any());

		String expectedMessage = "Número de matrícula já existe no banco de dados.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void updateStudentNoExistingIdTest () {
		StudentDTO studentDTO = new StudentDTO
				(1L, "nome", "sobrenome", "matricula", null);

		when(studentRepository.findById(2L)).thenReturn(Optional.of(studentDTO.build()));

		Exception exception = assertThrows(NotFoundException.class, () -> studentService.update(1L, studentDTO));

		verify(studentRepository, times(1)).findById(1L);
		verify(studentRepository, times(0)).findStudentByEnrollment("matricula");
		verify(studentRepository, times(0)).save(any());

		String expectedMessage = "Cadastro não existente";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void deleteStudentSuccess() {
		Student student = new Student
				(1L, "nome", "sobrenome", "matricula", null);

		when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

		studentService.delete(1L);

		verify(studentRepository, times(1)).findById(1L);
		verify(studentRepository, times(1)).deleteById(1L);
	}

	@Test
	void deleteStudentNoExistingIdTest() {
		Student student = new Student
				(1L, "nome", "sobrenome", "matricula", null);

		when(studentRepository.findById(2L)).thenReturn(Optional.of(student));

		Exception exception = assertThrows(NotFoundException.class, () -> studentService.delete(1L));

		verify(studentRepository, times(1)).findById(1L);
		verify(studentRepository, times(0)).deleteById(1L);

		String expectedMessage = "Cadastro não existente";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

}




