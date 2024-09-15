package com.universityproject.service;

import com.universityproject.model.Alumno;
import com.universityproject.model.Asignatura;
import com.universityproject.model.EstadoAsignatura;
import com.universityproject.model.Materia;
import com.universityproject.model.dto.AlumnoDTO;
import com.universityproject.model.dto.AsignaturaDTO;
import com.universityproject.model.exception.AlumnoNotFoundException;
import com.universityproject.model.exception.AsignaturaNotFoundException;
import com.universityproject.repository.AlumnoRepository;
import com.universityproject.repository.MateriaRepository;
import com.universityproject.service.implementation.AlumnoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlumnoServiceImplTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @Mock
    private MateriaRepository materiaRepository;

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearAlumno() {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId("1");
        alumnoDTO.setNombre("Juan");
        alumnoDTO.setApellido("Pérez");
        alumnoDTO.setDni("12345678");
        alumnoDTO.setAsignaturas(new ArrayList<>());

        Alumno alumno = new Alumno();
        alumno.setId("1");
        alumno.setNombre("Juan");
        alumno.setApellido("Pérez");
        alumno.setDni("12345678");

        when(materiaRepository.findByNombre(anyString())).thenReturn(Optional.of(new Materia()));
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumno);

        AlumnoDTO result = alumnoService.crearAlumno(alumnoDTO);
        assertEquals("1", result.getId());
        assertEquals("Juan", result.getNombre());
    }

    @Test
    public void testModificarAlumno() {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId("1");
        alumnoDTO.setNombre("Juan");
        alumnoDTO.setApellido("Pérez");
        alumnoDTO.setDni("12345678");

        Alumno alumno = new Alumno();
        alumno.setId("1");
        alumno.setNombre("Juan");
        alumno.setApellido("Pérez");
        alumno.setDni("12345678");

        when(alumnoRepository.findById(anyString())).thenReturn(Optional.of(alumno));
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumno);

        AlumnoDTO result = alumnoService.modificarAlumno("1", alumnoDTO);
        assertEquals("1", result.getId());
        assertEquals("Juan", result.getNombre());
    }

    @Test
    public void testEliminarAlumno() {
        Alumno alumno = new Alumno();
        alumno.setId("1");

        when(alumnoRepository.findById(anyString())).thenReturn(Optional.of(alumno));

        alumnoService.eliminarAlumno("1");
        verify(alumnoRepository, times(1)).delete(alumno);
    }

    @Test
    public void testObtenerAlumnoPorId() {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId("1");
        alumnoDTO.setNombre("Juan");

        Alumno alumno = new Alumno();
        alumno.setId("1");
        alumno.setNombre("Juan");

        when(alumnoRepository.findById(anyString())).thenReturn(Optional.of(alumno));

        AlumnoDTO result = alumnoService.obtenerAlumnoPorId("1");
        assertEquals("1", result.getId());
        assertEquals("Juan", result.getNombre());
    }

    @Test
    public void testListarAlumnos() {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId("1");
        alumnoDTO.setNombre("Juan");

        Alumno alumno = new Alumno();
        alumno.setId("1");
        alumno.setNombre("Juan");

        when(alumnoRepository.findAll()).thenReturn(Collections.singletonList(alumno));

        List<AlumnoDTO> result = alumnoService.listarAlumnos();
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Juan", result.get(0).getNombre());
    }
}





