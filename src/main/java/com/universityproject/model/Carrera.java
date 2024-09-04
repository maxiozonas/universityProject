package com.universityproject.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "carreras")
@Data
public class Carrera {
    @Id
    private String id;
    private String nombre;
    private String codigo;
    private int departamento;
    private int cantidadCuatrimestres;
    private List<String> materiasIds = new ArrayList<>();
}

