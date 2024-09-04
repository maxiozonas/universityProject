package com.universityproject.controller;

import com.universityproject.model.dto.AlumnoDto;
import com.universityproject.model.EstadoAsignatura;
import com.universityproject.service.AlumnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AlumnoControllerTest {

    @Mock
    private AlumnoService alumnoService;

    @InjectMocks
    private AlumnoController alumnoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearAlumno() {
        // Arrange
        AlumnoDto alumnoDto = new AlumnoDto();
        AlumnoDto alumnoCreado = new AlumnoDto();

        when(alumnoService.crearAlumno(alumnoDto)).thenReturn(alumnoCreado);

        // Act
        ResponseEntity<AlumnoDto> response = alumnoController.crearAlumno(alumnoDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(alumnoCreado, response.getBody());
    }

    @Test
    void testObtenerTodosLosAlumnos() {
        // Arrange
        List<AlumnoDto> alumnos = new ArrayList<>();
        alumnos.add(new AlumnoDto());
        alumnos.add(new AlumnoDto());

        when(alumnoService.obtenerTodosLosAlumnos()).thenReturn(alumnos);

        // Act
        ResponseEntity<List<AlumnoDto>> response = alumnoController.obtenerTodosLosAlumnos();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(alumnos, response.getBody());
    }

    @Test
    void testObtenerAlumnoPorId() {
        // Arrange
        AlumnoDto alumnoDto = new AlumnoDto();
        when(alumnoService.obtenerAlumnoPorId("123")).thenReturn(alumnoDto);

        // Act
        ResponseEntity<AlumnoDto> response = alumnoController.obtenerAlumnoPorId("123");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(alumnoDto, response.getBody());
    }

    @Test
    void testActualizarAlumno() {
        // Arrange
        String id = "123";
        AlumnoDto alumnoDto = new AlumnoDto();
        AlumnoDto alumnoActualizado = new AlumnoDto();

        when(alumnoService.actualizarAlumno(id, alumnoDto)).thenReturn(alumnoActualizado);

        // Act
        ResponseEntity<AlumnoDto> response = alumnoController.actualizarAlumno(id, alumnoDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(alumnoActualizado, response.getBody());
    }

    @Test
    void testEliminarAlumno() {
        // Arrange
        String id = "123";

        // Act
        ResponseEntity<Void> response = alumnoController.eliminarAlumno(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(alumnoService, times(1)).eliminarAlumno(id);
    }

    @Test
    void testActualizarAsignaturaDeAlumno() {
        // Arrange
        String idAlumno = "123";
        String idAsignatura = "456";
        EstadoAsignatura nuevoEstado = EstadoAsignatura.APROBADA;
        AlumnoDto alumnoActualizado = new AlumnoDto();

        when(alumnoService.actualizarAsignaturaDeAlumno(idAlumno, idAsignatura, nuevoEstado)).thenReturn(alumnoActualizado);

        // Act
        ResponseEntity<AlumnoDto> response = alumnoController.actualizarAsignaturaDeAlumno(idAlumno, idAsignatura, nuevoEstado);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(alumnoActualizado, response.getBody());
    }
}
