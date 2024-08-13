package com.universityproject.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "alumnos")
@Data
public class Alumno {

    @Id
    private String id;
    private String nombre;
    private String apellido;
    private String dni;
    private List<Asignatura> asignaturas;
}