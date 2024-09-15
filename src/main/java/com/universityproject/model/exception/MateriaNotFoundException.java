package com.universityproject.model.exception;

/**
 * Excepción lanzada cuando no se encuentra una materia por su ID.
 */
public class MateriaNotFoundException extends RuntimeException {
    public MateriaNotFoundException(String message) {
        super(message);
    }
}
