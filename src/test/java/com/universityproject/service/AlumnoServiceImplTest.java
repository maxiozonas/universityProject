package com.universityproject.service;

import com.universityproject.model.Alumno;
import com.universityproject.model.Asignatura;
import com.universityproject.model.EstadoAsignatura;
import com.universityproject.model.Materia;
import com.universityproject.model.dto.AlumnoDTO;
import com.universityproject.model.dto.AsignaturaDTO;
import com.universityproject.repository.AlumnoRepository;
import com.universityproject.repository.MateriaRepository;
import com.universityproject.service.exception.MateriaNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AlumnoServiceImplTest {

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    @Mock
    private AlumnoRepository alumnoRepository;

    @Mock
    private MateriaRepository materiaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearAlumno() {
        // Crear AlumnoDTO
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Juan");
        alumnoDTO.setApellido("Perez");
        alumnoDTO.setDni("12345678");

        AsignaturaDTO asignaturaDTO = new AsignaturaDTO();
        asignaturaDTO.setMateriaNombre("Matemáticas");

        List<AsignaturaDTO> asignaturasDTO = new ArrayList<>();
        asignaturasDTO.add(asignaturaDTO);
        alumnoDTO.setAsignaturas(asignaturasDTO);

        // Simulación de materia en base de datos
        when(materiaRepository.findByNombre("Matemáticas")).thenReturn(Optional.of(mockMateria("66d5b3b773250604d1a73504")));

        // Simulación de guardar el alumno
        Alumno alumno = new Alumno();
        alumno.setNombre("Juan");
        alumno.setApellido("Perez");
        alumno.setDni("12345678");
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumno);

        // Ejecutar método
        AlumnoDTO result = alumnoService.crearAlumno(alumnoDTO);

        // Verificaciones
        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        assertEquals("Perez", result.getApellido());
        verify(alumnoRepository, times(1)).save(any(Alumno.class));
    }

    @Test
    public void testModificarAlumno() {
        String alumnoId = "66d5b3b773250604d1a73505";
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Carlos");
        alumnoDTO.setApellido("Lopez");
        alumnoDTO.setDni("87654321");

        Alumno alumno = new Alumno();
        alumno.setId(alumnoId);
        alumno.setNombre("Juan");
        alumno.setApellido("Perez");

        when(alumnoRepository.findById(alumnoId)).thenReturn(Optional.of(alumno));
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumno);

        AlumnoDTO result = alumnoService.modificarAlumno(alumnoId, alumnoDTO);

        assertNotNull(result);
        assertEquals("Carlos", result.getNombre());
        assertEquals("Lopez", result.getApellido());
        verify(alumnoRepository, times(1)).save(any(Alumno.class));
    }

    @Test
    public void testEliminarAlumno() {
        String alumnoId = "66d5b3b773250604d1a73505";
        Alumno alumno = new Alumno();
        alumno.setId(alumnoId);

        when(alumnoRepository.findById(alumnoId)).thenReturn(Optional.of(alumno));

        alumnoService.eliminarAlumno(alumnoId);

        verify(alumnoRepository, times(1)).delete(alumno);
    }

    @Test
    public void testObtenerAlumnoPorId() {
        String alumnoId = "66d5b3b773250604d1a73505";
        Alumno alumno = new Alumno();
        alumno.setId(alumnoId);
        alumno.setNombre("Juan");

        when(alumnoRepository.findById(alumnoId)).thenReturn(Optional.of(alumno));

        AlumnoDTO result = alumnoService.obtenerAlumnoPorId(alumnoId);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        verify(alumnoRepository, times(1)).findById(alumnoId);
    }

    @Test
    public void testListarAlumnos() {
        List<Alumno> alumnos = new ArrayList<>();
        Alumno alumno1 = new Alumno();
        alumno1.setNombre("Juan");
        Alumno alumno2 = new Alumno();
        alumno2.setNombre("Carlos");

        alumnos.add(alumno1);
        alumnos.add(alumno2);

        when(alumnoRepository.findAll()).thenReturn(alumnos);

        List<AlumnoDTO> result = alumnoService.listarAlumnos();

        assertEquals(2, result.size());
        assertEquals("Juan", result.get(0).getNombre());
        assertEquals("Carlos", result.get(1).getNombre());
    }

    @Test
    public void testActualizarEstadoAsignatura() {
        String alumnoId = "66d5b3b773250604d1a73505";
        String asignaturaId = "66d5cd37cd6b6e4701d7bf89";
        EstadoAsignatura nuevoEstado = EstadoAsignatura.APROBADA;

        Asignatura asignatura = new Asignatura();
        asignatura.setMateriaId(asignaturaId);
        asignatura.setEstadoAsignatura(EstadoAsignatura.NO_CURSADA);

        Alumno alumno = new Alumno();
        alumno.setId(alumnoId);
        alumno.setAsignaturas(new ArrayList<>());
        alumno.getAsignaturas().add(asignatura);

        when(alumnoRepository.findById(alumnoId)).thenReturn(Optional.of(alumno));

        Materia materia = mockMateria(asignaturaId);
        when(materiaRepository.findById(asignaturaId)).thenReturn(Optional.of(materia));

        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumno);

        AlumnoDTO result = alumnoService.actualizarEstadoAsignatura(alumnoId, asignaturaId, nuevoEstado);

        assertNotNull(result);
        assertEquals(EstadoAsignatura.APROBADA, result.getAsignaturas().get(0).getEstadoAsignatura());

        verify(alumnoRepository, times(1)).save(any(Alumno.class));
    }

    // Método para simular una materia
    private Materia mockMateria(String id) {
        Materia materia = new Materia();
        materia.setId(id);
        materia.setNombre("Matemáticas"); // Puedes cambiar esto según tus necesidades
        return materia;
    }

}


