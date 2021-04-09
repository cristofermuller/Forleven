package com.forleven.api;

import com.forleven.api.student.Student;
import com.forleven.api.student.repository.StudentRepository;
import com.forleven.api.student.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;
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

		Exception exception = assertThrows(ResponseStatusException.class, () ->
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
		ArgumentCaptor<Student> studentArgumentCaptor =
				ArgumentCaptor.forClass(Student.class);

		when(studentRepository.save(any())).thenReturn(student);

		studentService.create(student);

		verify(studentRepository, times(1)).save(studentArgumentCaptor.capture());
		verify(studentRepository, times(1)).findStudentByEnrollment(any());

		Student capturedStudent = studentArgumentCaptor.getValue();

		assertEquals(student, capturedStudent);
	}

	@Test()
	void createStudentDuplicatedEnrollmentTest() {
		Student student = new Student
				(1L, "nome", "sobrenome", "matricula", null);

		when(studentRepository.findStudentByEnrollment("matricula")).thenReturn(Optional.of(student));

		Exception exception = assertThrows(ResponseStatusException.class, () -> studentService.create(student));

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
		ArgumentCaptor<Student> studentArgumentCaptor =
				ArgumentCaptor.forClass(Student.class);

		when(studentRepository.save(any())).thenReturn(student);
		when(studentRepository.findById(any())).thenReturn(Optional.of(student));

		studentService.update(1L, student);

		verify(studentRepository, times(1)).findById(1L);
		verify(studentRepository, times(1)).findStudentByEnrollment(any());
		verify(studentRepository, times(1)).save(studentArgumentCaptor.capture());

		Student capturedStudent = studentArgumentCaptor.getValue();

		assertEquals(student, capturedStudent);
	}

	@Test
	void updateStudentDuplicatedEnrollmentTest() {
		Student student = new Student
				(1L, "nome", "sobrenome", "matricula", null);

		when(studentRepository.findStudentByEnrollment("matricula")).thenReturn(Optional.of(student));
		when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

		Exception exception = assertThrows(ResponseStatusException.class, () -> studentService.update(1L, student));

		verify(studentRepository, times(1)).findById(1L);
		verify(studentRepository, times(1)).findStudentByEnrollment("matricula");
		verify(studentRepository, times(0)).save(any());

		String expectedMessage = "Número de matrícula já existe no banco de dados.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void updateStudentNoExistingIdTest () {
		Student student = new Student
				(1L, "nome", "sobrenome", "matricula", null);

		when(studentRepository.findById(2L)).thenReturn(Optional.of(student));

		Exception exception = assertThrows(ResponseStatusException.class, () -> studentService.update(1L, student));

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

		Exception exception = assertThrows(ResponseStatusException.class, () -> studentService.delete(1L));

		verify(studentRepository, times(1)).findById(1L);
		verify(studentRepository, times(0)).deleteById(1L);

		String expectedMessage = "Cadastro não existente";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

}




