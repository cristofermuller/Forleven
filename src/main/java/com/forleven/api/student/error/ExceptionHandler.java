package com.forleven.api.student.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;
import java.time.ZoneId;


@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    String america = "America/Sao_Paulo";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String errorResult = ex.getFieldError().getDefaultMessage();
        ExceptionFields exceptionFields = new ExceptionFields(LocalDateTime.now(ZoneId.of(america)), errorResult);

        return new ResponseEntity<>(exceptionFields, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    private final ResponseEntity<ExceptionFields> handleNotFoundException(NotFoundException ex, WebRequest request) {
        String errorResult = ex.getMessage();
        ExceptionFields exceptionFields = new ExceptionFields(LocalDateTime.now(ZoneId.of(america)), errorResult);
        return new ResponseEntity<>(exceptionFields, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConflictException.class)
    private final ResponseEntity<ExceptionFields> handleConflictException(ConflictException ex, WebRequest request) {
        String errorResult = ex.getMessage();
        ExceptionFields exceptionFields = new ExceptionFields(LocalDateTime.now(ZoneId.of(america)), errorResult);
        return new ResponseEntity<>(exceptionFields, HttpStatus.CONFLICT);
    }

}