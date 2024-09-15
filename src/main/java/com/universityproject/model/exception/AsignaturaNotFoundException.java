package com.universityproject.model.exception;

/**
 * Excepción lanzada cuando una asignatura asociada a un alumno no es encontrada.
 */
public class AsignaturaNotFoundException extends RuntimeException {
    public AsignaturaNotFoundException(String message) {
        super(message);
    }
}

