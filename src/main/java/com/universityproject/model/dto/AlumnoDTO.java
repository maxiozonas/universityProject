package com.universityproject.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class AlumnoDTO {

    private String id;
    private String nombre;
    private String apellido;
    private String dni;
    private List<AsignaturaDTO> asignaturas;
}
