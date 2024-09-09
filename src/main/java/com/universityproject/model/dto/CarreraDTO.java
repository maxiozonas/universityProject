package com.universityproject.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CarreraDTO {
    private String id;
    private String nombre;
    private String codigo;
    private int departamento;
    private int cantidadCuatrimestres;
    private List<String> materiasNombres;
}
