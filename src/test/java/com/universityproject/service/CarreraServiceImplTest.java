package com.universityproject.service;

import com.universityproject.model.Carrera;
import com.universityproject.model.Materia;
import com.universityproject.model.dto.CarreraDTO;
import com.universityproject.model.exception.CarreraNotFoundException;
import com.universityproject.repository.CarreraRepository;
import com.universityproject.repository.MateriaRepository;
import com.universityproject.service.implementation.CarreraServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CarreraServiceImplTest {

    @Mock
    private CarreraRepository carreraRepository;

    @Mock
    private MateriaRepository materiaRepository;

    @InjectMocks
    private CarreraServiceImpl carreraService;

    private Carrera carrera;
    private CarreraDTO carreraDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carrera = new Carrera();
        carrera.setId("1");
        carrera.setNombre("Ingeniería");
        carrera.setCodigo("ING-001");
        carrera.setDepartamento(1);
        carrera.setCantidadCuatrimestres(8);
        carrera.setMateriasIds(Arrays.asList("mat1", "mat2"));

        carreraDTO = new CarreraDTO();
        carreraDTO.setId("1");
        carreraDTO.setNombre("Ingeniería");
        carreraDTO.setCodigo("ING-001");
        carreraDTO.setDepartamento(1);
        carreraDTO.setCantidadCuatrimestres(8);
        carreraDTO.setMateriasNombres(Arrays.asList("Matemáticas", "Física"));
    }

    @Test
    void testCrearCarrera() {
        when(carreraRepository.save(any(Carrera.class))).thenReturn(carrera);
        CarreraDTO result = carreraService.crearCarrera(carreraDTO);
        assertEquals(carreraDTO.getNombre(), result.getNombre());
        verify(carreraRepository, times(1)).save(any(Carrera.class));
    }

    @Test
    void testModificarCarrera_CarreraNotFound() {
        when(carreraRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(CarreraNotFoundException.class, () -> carreraService.modificarCarrera("1", carreraDTO));
    }

    @Test
    void testModificarCarrera_Success() {
        when(carreraRepository.findById("1")).thenReturn(Optional.of(carrera));
        when(carreraRepository.save(any(Carrera.class))).thenReturn(carrera);

        CarreraDTO result = carreraService.modificarCarrera("1", carreraDTO);
        assertEquals(carreraDTO.getNombre(), result.getNombre());
        verify(carreraRepository, times(1)).findById("1");
        verify(carreraRepository, times(1)).save(any(Carrera.class));
    }

    @Test
    void testEliminarCarrera_CarreraNotFound() {
        when(carreraRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(CarreraNotFoundException.class, () -> carreraService.eliminarCarrera("1"));
    }

    @Test
    void testEliminarCarrera_Success() {
        when(carreraRepository.findById("1")).thenReturn(Optional.of(carrera));
        carreraService.eliminarCarrera("1");
        verify(carreraRepository, times(1)).findById("1");
        verify(carreraRepository, times(1)).delete(any(Carrera.class));
    }

    @Test
    void testObtenerCarreraPorId_CarreraNotFound() {
        when(carreraRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(CarreraNotFoundException.class, () -> carreraService.obtenerCarreraPorId("1"));
    }

    @Test
    void testObtenerCarreraPorId_Success() {
        when(carreraRepository.findById("1")).thenReturn(Optional.of(carrera));
        CarreraDTO result = carreraService.obtenerCarreraPorId("1");
        assertEquals(carreraDTO.getNombre(), result.getNombre());
        verify(carreraRepository, times(1)).findById("1");
    }
}


