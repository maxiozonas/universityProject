package com.universityproject.service;

import com.universityproject.model.dto.MateriaDTO;

import java.util.List;

public interface MateriaService {
    MateriaDTO crearMateria(MateriaDTO materiaDTO);

    MateriaDTO modificarMateria(String id, MateriaDTO materiaDTO);

    void eliminarMateria(String id);

    MateriaDTO obtenerMateriaPorId(String id);

    List<MateriaDTO> listarMaterias();

    List<MateriaDTO> obtenerMateriasPorNombre(String nombre);

    List<MateriaDTO> obtenerMateriasOrdenadas(String ordenBy);

}
