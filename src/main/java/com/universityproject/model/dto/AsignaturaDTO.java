package com.universityproject.model.dto;

import com.universityproject.model.EstadoAsignatura;
import lombok.Data;

@Data
public class AsignaturaDTO {
    private String materiaNombre;
    private EstadoAsignatura estadoAsignatura;
}
