package com.universityproject.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "materias")
@Data
public class Materia {

    @Id
    private String id;
    private String nombre;
    private int anio;
    private int cuatrimestre;
    private List<String> correlativasIds = new ArrayList<>();
    private String carreraId;
}
