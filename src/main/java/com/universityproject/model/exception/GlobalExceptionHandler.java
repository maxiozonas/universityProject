package com.universityproject.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador global de excepciones para manejar errores en toda la aplicación.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja la excepción AlumnoNotFoundException
    @ExceptionHandler(AlumnoNotFoundException.class)
    public ResponseEntity<String> handleAlumnoNotFoundException(AlumnoNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Maneja la excepción MateriaNotFoundException
    @ExceptionHandler(MateriaNotFoundException.class)
    public ResponseEntity<String> handleMateriaNotFoundException(MateriaNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Maneja la excepción CarreraNotFoundException
    @ExceptionHandler(CarreraNotFoundException.class)
    public ResponseEntity<String> handleCarreraNotFoundException(CarreraNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Maneja la excepción AlumnoInvalidDataException
    @ExceptionHandler(AlumnoInvalidDataException.class)
    public ResponseEntity<String> handleAlumnoInvalidDataException(AlumnoInvalidDataException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Maneja la excepción MateriaInvalidDataException
    @ExceptionHandler(MateriaInvalidDataException.class)
    public ResponseEntity<String> handleMateriaInvalidDataException(MateriaInvalidDataException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Maneja la excepción MateriaInvalidDataException
    @ExceptionHandler(CarreraInvalidDataException.class)
    public ResponseEntity<String> handleCarreraInvalidDataException(CarreraInvalidDataException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Maneja la excepción AsignaturaNotFoundException
    @ExceptionHandler(AsignaturaNotFoundException.class)
    public ResponseEntity<String> handleAsignaturaNotFoundException(AsignaturaNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Maneja otras excepciones genéricas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex, WebRequest request) {
        ex.printStackTrace();  // Muestra el stack trace en los logs para depuración.
        return new ResponseEntity<>("Error interno del servidor: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Maneja excepciones de validación de datos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

