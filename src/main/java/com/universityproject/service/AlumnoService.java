package com.universityproject.service;

import com.universityproject.dto.AlumnoDto;
import com.universityproject.model.EstadoAsignatura;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AlumnoService {
    AlumnoDto crearAlumno(AlumnoDto AlumnoDto);
    List<AlumnoDto> obtenerTodosLosAlumnos();
    AlumnoDto obtenerAlumnoPorId(String id);
    AlumnoDto actualizarAlumno(String id, AlumnoDto AlumnoDto);
    AlumnoDto actualizarAsignaturaDeAlumno(String idAlumno, String idAsignatura, EstadoAsignatura nuevoEstado);
    void eliminarAlumno(String id);
}
