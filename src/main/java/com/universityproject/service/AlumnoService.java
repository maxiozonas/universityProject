package com.universityproject.service;

import com.universityproject.model.dto.AlumnoDTO;
import com.universityproject.model.EstadoAsignatura;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AlumnoService {
    AlumnoDTO crearAlumno(AlumnoDTO alumnoDTO);
    AlumnoDTO modificarAlumno(String id, AlumnoDTO alumnoDTO);
    void eliminarAlumno(String id);
    AlumnoDTO obtenerAlumnoPorId(String id);
    List<AlumnoDTO> listarAlumnos();
    AlumnoDTO actualizarEstadoAsignatura(String idAlumno, String idAsignatura, EstadoAsignatura estado);
}
