package com.universityproject.service;

import com.universityproject.model.dto.MateriaDTO;

import java.util.List;

public interface MateriaService {
    MateriaDTO crearMateria(MateriaDTO materiaDTO);

    MateriaDTO modificarMateria(String id, MateriaDTO materiaDTO);

    void eliminarMateria(String id);

    MateriaDTO ObtenerMateriaPorId(String id);

    List<MateriaDTO> ObtenerMaterias();

    List<MateriaDTO> ObtenerMateriasPorNombre(String nombre);

    List<MateriaDTO> ObtenerMateriasOrdenadas(String ordenBy);
}
