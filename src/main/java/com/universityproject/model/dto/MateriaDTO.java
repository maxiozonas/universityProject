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
    private List<String> correlativasIds; // Se mantiene para envíos
    private CarreraSimpleDTO carrera; // Muestra el id y nombre de la carrera
    private List<CorrelativaSimpleDTO> correlativas; // Lista de correlativas con id y nombre
}
