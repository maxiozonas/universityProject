package com.universityproject.model.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MateriaDTO {

    private String id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    private String nombre;

    @NotNull(message = "El año no puede ser nulo")
    @Min(value = 1, message = "El año debe ser mayor o igual a 1")
    private int anio;

    @NotNull(message = "El cuatrimestre no puede ser nulo")
    @Min(value = 1, message = "El cuatrimestre debe ser mayor o igual a 1")
    private int cuatrimestre;

    private List<String> correlativasIds; // Se mantiene para envíos

    @NotNull(message = "La carrera no puede ser nula")
    private CarreraSimpleDTO carrera; // Muestra el id y nombre de la carrera

    private List<CorrelativaSimpleDTO> correlativas; // Lista de correlativas con id y nombre
}
