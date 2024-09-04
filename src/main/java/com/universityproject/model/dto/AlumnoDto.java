package com.universityproject.model.dto;

import com.universityproject.model.Asignatura;
import lombok.Data;

import java.util.List;

@Data
public class AlumnoDto {

    private String id;
    private String nombre;
    private String apellido;
    private String dni;
    private List<Asignatura> asignaturas;
}
