package com.universityproject.model.exception;

/**
 * Excepción lanzada cuando no se encuentra una carrera por su ID.
 */
public class CarreraNotFoundException extends RuntimeException {
    public CarreraNotFoundException(String message) {
        super(message);
    }
}