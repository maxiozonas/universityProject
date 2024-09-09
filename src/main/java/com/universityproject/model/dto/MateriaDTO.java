package com.universityproject.model.dto;

import com.universityproject.model.Materia;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MateriaDTO {
    private String id;
    private String nombre;
    private int anio;
    private int cuatrimestre;
    private List<String> correlativasIds; // IDs de las materias correlativas
    private String carreraId;

    private List<String> correlativasNombres = new ArrayList<>();
    private String carreraNombre;
}
