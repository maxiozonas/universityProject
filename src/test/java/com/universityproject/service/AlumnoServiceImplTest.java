package com.universityproject.service;

import com.universityproject.model.dto.AlumnoDto;
import com.universityproject.model.Alumno;
import com.universityproject.model.Asignatura;
import com.universityproject.model.EstadoAsignatura;
import com.universityproject.repository.AlumnoRepository;
import com.universityproject.service.exception.AlumnoNotFoundException;
import com.universityproject.service.implementation.AlumnoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlumnoServiceImplTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearAlumno() {
        // Arrange
        AlumnoDto alumnoDto = new AlumnoDto();
        Alumno alumno = new Alumno();
        Alumno alumnoGuardado = new Alumno();

        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumnoGuardado);

        // Act
        AlumnoDto resultado = alumnoService.crearAlumno(alumnoDto);

        // Assert
        assertNotNull(resultado);
        assertEquals(alumnoGuardado.getId(), resultado.getId());
    }

    @Test
    void testObtenerTodosLosAlumnos() {
        // Arrange
        List<Alumno> alumnos = new ArrayList<>();
        alumnos.add(new Alumno());
        alumnos.add(new Alumno());

        when(alumnoRepository.findAll()).thenReturn(alumnos);

        // Act
        List<AlumnoDto> resultado = alumnoService.obtenerTodosLosAlumnos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    void testObtenerAlumnoPorId() {
        // Arrange
        Alumno alumno = new Alumno();
        when(alumnoRepository.findById("123")).thenReturn(Optional.of(alumno));

        // Act
        AlumnoDto resultado = alumnoService.obtenerAlumnoPorId("123");

        // Assert
        assertNotNull(resultado);
        assertEquals(alumno.getId(), resultado.getId());
    }

    @Test
    void testObtenerAlumnoPorIdNoEncontrado() {
        // Arrange
        when(alumnoRepository.findById("123")).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(AlumnoNotFoundException.class, () -> alumnoService.obtenerAlumnoPorId("123"));
    }

    @Test
    void testActualizarAlumno() {
        // Arrange
        String id = "123";
        AlumnoDto alumnoDto = new AlumnoDto();
        Alumno alumno = new Alumno();
        Alumno alumnoActualizado = new Alumno();

        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumnoActualizado);

        // Act
        AlumnoDto resultado = alumnoService.actualizarAlumno(id, alumnoDto);

        // Assert
        assertNotNull(resultado);
        assertEquals(alumnoActualizado.getId(), resultado.getId());
    }

    @Test
    void testEliminarAlumno() {
        // Arrange
        String id = "123";

        // Act
        alumnoService.eliminarAlumno(id);

        // Assert
        verify(alumnoRepository, times(1)).deleteById(id);
    }

    @Test
    void testActualizarAsignaturaDeAlumno() {
        // Arrange
        String idAlumno = "123";
        String idAsignatura = "456";
        EstadoAsignatura nuevoEstado = EstadoAsignatura.APROBADA;
        Alumno alumno = new Alumno();
        Asignatura asignatura = new Asignatura();
        asignatura.setId(idAsignatura);
        alumno.setAsignaturas(List.of(asignatura));

        when(alumnoRepository.findById(idAlumno)).thenReturn(Optional.of(alumno));
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumno);

        // Act
        AlumnoDto resultado = alumnoService.actualizarAsignaturaDeAlumno(idAlumno, idAsignatura, nuevoEstado);

        // Assert
        assertNotNull(resultado);
        assertEquals(nuevoEstado, resultado.getAsignaturas().get(0).getEstado());
    }

    @Test
    void testActualizarAsignaturaDeAlumnoAlumnoNoEncontrado() {
        // Arrange
        String idAlumno = "123";
        String idAsignatura = "456";
        EstadoAsignatura nuevoEstado = EstadoAsignatura.APROBADA;

        when(alumnoRepository.findById(idAlumno)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(AlumnoNotFoundException.class, () -> alumnoService.actualizarAsignaturaDeAlumno(idAlumno, idAsignatura, nuevoEstado));
    }
}
