package com.universityproject.model.exception;

/**
 * Excepción lanzada cuando no se encuentra un alumno por su ID.
 */
public class AlumnoNotFoundException extends RuntimeException {
    public AlumnoNotFoundException(String message) {
        super(message);
    }
}
