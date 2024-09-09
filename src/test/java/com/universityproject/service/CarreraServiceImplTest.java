package com.universityproject.service;

import com.universityproject.model.Carrera;
import com.universityproject.model.dto.CarreraDTO;
import com.universityproject.repository.CarreraRepository;
import com.universityproject.service.implementation.CarreraServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CarreraServiceImplTest {

    @Mock
    private CarreraRepository carreraRepository;

    @InjectMocks
    private CarreraServiceImpl carreraService;

    private Carrera carrera;
    private CarreraDTO carreraDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carrera = new Carrera();
        carrera.setId("1");
        carrera.setNombre("Ingeniería en Sistemas");
        carrera.setCodigo("IS2024");
        carrera.setDepartamento(101);
        carrera.setCantidadCuatrimestres(10);

        carreraDTO = new CarreraDTO();
        carreraDTO.setId(carrera.getId());
        carreraDTO.setNombre(carrera.getNombre());
        carreraDTO.setCodigo(carrera.getCodigo());
        carreraDTO.setDepartamento(carrera.getDepartamento());
        carreraDTO.setCantidadCuatrimestres(carrera.getCantidadCuatrimestres());
        carreraDTO.setMateriasNombres(carrera.getMateriasIds());
    }

    @Test
    void testCrearCarrera() {
        when(carreraRepository.save(any(Carrera.class))).thenReturn(carrera);

        CarreraDTO result = carreraService.crearCarrera(carreraDTO);

        assertEquals(carreraDTO, result);
        verify(carreraRepository, times(1)).save(any(Carrera.class));
    }

    @Test
    void testModificarCarrera() {
        when(carreraRepository.findById("1")).thenReturn(Optional.of(carrera));
        when(carreraRepository.save(any(Carrera.class))).thenReturn(carrera);

        CarreraDTO result = carreraService.modificarCarrera("1", carreraDTO);

        assertEquals(carreraDTO, result);
        verify(carreraRepository, times(1)).findById("1");
        verify(carreraRepository, times(1)).save(any(Carrera.class));
    }

    @Test
    void testEliminarCarrera() {
        doNothing().when(carreraRepository).deleteById("1");

        carreraService.eliminarCarrera("1");

        verify(carreraRepository, times(1)).deleteById("1");
    }

    @Test
    void testListarCarreras() {
        when(carreraRepository.findAll()).thenReturn(Arrays.asList(carrera));

        List<CarreraDTO> result = carreraService.listarCarreras();

        assertEquals(1, result.size());
        assertEquals(carreraDTO, result.get(0));
        verify(carreraRepository, times(1)).findAll();
    }

    @Test
    void testObtenerCarreraPorId() {
        when(carreraRepository.findById("1")).thenReturn(Optional.of(carrera));

        CarreraDTO result = carreraService.obtenerCarreraPorId("1");

        assertEquals(carreraDTO, result);
        verify(carreraRepository, times(1)).findById("1");
    }
}

