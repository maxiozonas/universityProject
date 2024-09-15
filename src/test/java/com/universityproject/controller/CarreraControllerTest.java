package com.universityproject.controller;

import com.universityproject.model.Carrera;
import com.universityproject.model.dto.CarreraDTO;
import com.universityproject.model.exception.CarreraNotFoundException;
import com.universityproject.service.CarreraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CarreraControllerTest {

    @Mock
    private CarreraService carreraService;

    @InjectMocks
    private CarreraController carreraController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearCarrera() {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setId("1");
        carreraDTO.setNombre("Ingeniería Civil");
        carreraDTO.setCodigo("IC123");
        carreraDTO.setDepartamento(1);
        carreraDTO.setCantidadCuatrimestres(10);

        when(carreraService.crearCarrera(any(CarreraDTO.class))).thenReturn(carreraDTO);

        CarreraDTO result = carreraController.crearCarrera(carreraDTO);
        assertEquals("1", result.getId());
        assertEquals("Ingeniería Civil", result.getNombre());
    }

    @Test
    public void testModificarCarrera() {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setId("1");
        carreraDTO.setNombre("Ingeniería Civil");
        carreraDTO.setCodigo("IC123");
        carreraDTO.setDepartamento(1);
        carreraDTO.setCantidadCuatrimestres(10);

        when(carreraService.modificarCarrera(anyString(), any(CarreraDTO.class))).thenReturn(carreraDTO);

        CarreraDTO result = carreraController.modificarCarrera("1", carreraDTO);
        assertEquals("1", result.getId());
        assertEquals("Ingeniería Civil", result.getNombre());
    }

    @Test
    public void testEliminarCarrera() {
        doNothing().when(carreraService).eliminarCarrera(anyString());

        carreraController.eliminarCarrera("1");
        verify(carreraService, times(1)).eliminarCarrera("1");
    }

    @Test
    public void testListarCarreras() {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setId("1");
        carreraDTO.setNombre("Ingeniería Civil");

        when(carreraService.listarCarreras()).thenReturn(Collections.singletonList(carreraDTO));

        List<CarreraDTO> result = carreraController.listarCarreras();
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Ingeniería Civil", result.get(0).getNombre());
    }

    @Test
    public void testObtenerCarreraPorId() {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setId("1");
        carreraDTO.setNombre("Ingeniería Civil");

        when(carreraService.obtenerCarreraPorId(anyString())).thenReturn(carreraDTO);

        CarreraDTO result = carreraController.obtenerCarreraPorId("1");
        assertEquals("1", result.getId());
        assertEquals("Ingeniería Civil", result.getNombre());
    }
}


