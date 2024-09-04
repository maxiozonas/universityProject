package com.universityproject.controller;

import com.universityproject.model.dto.AlumnoDto;
import com.universityproject.model.EstadoAsignatura;
import com.universityproject.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @PostMapping
    public ResponseEntity<AlumnoDto> crearAlumno(@RequestBody AlumnoDto AlumnoDto) {
        AlumnoDto alumnoCreado = alumnoService.crearAlumno(AlumnoDto);
        return new ResponseEntity<>(alumnoCreado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AlumnoDto>> obtenerTodosLosAlumnos() {
        List<AlumnoDto> alumnos = alumnoService.obtenerTodosLosAlumnos();
        return new ResponseEntity<>(alumnos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoDto> obtenerAlumnoPorId(@PathVariable String id) {
        AlumnoDto alumno = alumnoService.obtenerAlumnoPorId(id);
        return new ResponseEntity<>(alumno, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlumnoDto> actualizarAlumno(@PathVariable String id, @RequestBody AlumnoDto alumnoDto) {
        AlumnoDto alumnoActualizado = alumnoService.actualizarAlumno(id, alumnoDto);
        return new ResponseEntity<>(alumnoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable String id) {
        alumnoService.eliminarAlumno(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/alumno/{idAlumno}/asignatura/{idAsignatura}")
    public ResponseEntity<AlumnoDto> actualizarAsignaturaDeAlumno(@PathVariable String idAlumno,
                                                                  @PathVariable String idAsignatura,
                                                                  @RequestBody EstadoAsignatura nuevoEstado) {
        AlumnoDto alumnoActualizado = alumnoService.actualizarAsignaturaDeAlumno(idAlumno, idAsignatura, nuevoEstado);
        return ResponseEntity.ok(alumnoActualizado);
    }
}
