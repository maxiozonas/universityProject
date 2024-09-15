package com.universityproject.model.exception;

/**
 * Excepción lanzada cuando los datos proporcionados para un alumno son inválidos.
 */
public class AlumnoInvalidDataException extends RuntimeException {
    public AlumnoInvalidDataException(String message) {
        super(message);
    }
}

