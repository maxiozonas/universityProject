package com.universityproject.service;

import com.universityproject.dto.MateriaDto;
import com.universityproject.model.Materia;

import java.util.List;

public interface MateriaService {
    Materia crearMateria(MateriaDto materiaDto);
    Materia actualizarMateria(String id, MateriaDto materiaDto);
    void eliminarMateria(String id);
    List<Materia> obtenerMateriasPorNombre(String nombre, String order);
}
