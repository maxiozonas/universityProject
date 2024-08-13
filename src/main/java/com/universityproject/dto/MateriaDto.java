package com.universityproject.dto;

import com.universityproject.model.Materia;
import lombok.Data;

import java.util.List;

@Data
public class MateriaDto {
    private String nombre;
    private int anio;
    private int cuatrimestre;
    private List<Materia> correlativas;
}
