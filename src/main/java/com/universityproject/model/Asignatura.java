package com.universityproject.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "asignaturas")
@Data
public class Asignatura {
    @Id
    private String id;
    private String materiaId;
    private EstadoAsignatura estadoAsignatura;
}