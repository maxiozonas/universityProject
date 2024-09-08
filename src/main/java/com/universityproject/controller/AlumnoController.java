package com.universityproject.controller;

import com.universityproject.model.EstadoAsignatura;
import com.universityproject.model.dto.AlumnoDTO;
import com.universityproject.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para manejar las operaciones CRUD relacionadas con los alumnos.
 */
@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    /**
     * Crea un nuevo alumno en el sistema.
     * @param alumnoDTO Objeto DTO que contiene los datos del alumno.
     * @return El DTO del alumno recién creado.
     */
    @PostMapping
    public AlumnoDTO crearAlumno(@RequestBody AlumnoDTO alumnoDTO) {
        return alumnoService.crearAlumno(alumnoDTO);
    }

    /**
     * Modifica un alumno existente.
     * @param idAlumno El ID del alumno a modificar.
     * @param alumnoDTO El objeto DTO con los nuevos datos del alumno.
     * @return El DTO del alumno modificado.
     */
    @PutMapping("/{idAlumno}")
    public AlumnoDTO modificarAlumno(@PathVariable String idAlumno, @RequestBody AlumnoDTO alumnoDTO) {
        return alumnoService.modificarAlumno(idAlumno, alumnoDTO);
    }

    /**
     * Elimina un alumno del sistema.
     * @param idAlumno El ID del alumno a eliminar.
     */
    @DeleteMapping("/{idAlumno}")
    public void eliminarAlumno(@PathVariable String idAlumno) {
        alumnoService.eliminarAlumno(idAlumno);
    }

    /**
     * Obtiene un alumno por su ID.
     * @param idAlumno El ID del alumno que se desea obtener.
     * @return El DTO del alumno correspondiente al ID proporcionado.
     */
    @GetMapping("/{idAlumno}")
    public AlumnoDTO obtenerAlumnoPorId(@PathVariable String idAlumno) {
        return alumnoService.obtenerAlumnoPorId(idAlumno);
    }

    /**
     * Lista todos los alumnos registrados.
     * @return Una lista de DTOs de todos los alumnos.
     */
    @GetMapping
    public List<AlumnoDTO> listarAlumnos() {
        return alumnoService.listarAlumnos();
    }

    /**
     * Actualiza el estado de una asignatura para un alumno.
     * @param idAlumno El ID del alumno.
     * @param idAsignatura El ID de la asignatura.
     * @param estado El nuevo estado de la asignatura.
     * @return El DTO del alumno con el estado de la asignatura actualizado.
     */
    @PutMapping("/{idAlumno}/asignatura/{idAsignatura}")
    public AlumnoDTO actualizarEstadoAsignatura(@PathVariable String idAlumno, @PathVariable String idAsignatura, @RequestBody EstadoAsignatura estado) {
        return alumnoService.actualizarEstadoAsignatura(idAlumno, idAsignatura, estado);
    }
}

