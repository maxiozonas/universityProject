package com.universityproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.universityproject.model.Carrera;
import com.universityproject.model.dto.CarreraDTO;
import com.universityproject.service.CarreraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class CarreraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarreraService carreraService;

    @Autowired
    private ObjectMapper objectMapper;

    private CarreraDTO carreraDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        carreraDTO = new CarreraDTO();
        carreraDTO.setId("1");
        carreraDTO.setNombre("Ingeniería en Sistemas");
        carreraDTO.setCodigo("IS001");
        carreraDTO.setDepartamento(1);
        carreraDTO.setCantidadCuatrimestres(10);
        carreraDTO.setMateriasIds(Arrays.asList("materia1", "materia2"));
    }

    @Test
    public void testCrearCarrera() throws Exception {
        when(carreraService.crearCarrera(carreraDTO)).thenReturn(carreraDTO);

        mockMvc.perform(post("/carreras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carreraDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nombre").value("Ingeniería en Sistemas"))
                .andExpect(jsonPath("$.codigo").value("IS001"))
                .andExpect(jsonPath("$.departamento").value(1))
                .andExpect(jsonPath("$.cantidadCuatrimestres").value(10))
                .andExpect(jsonPath("$.materiasIds[0]").value("materia1"));
    }

    @Test
    public void testModificarCarrera() throws Exception {
        when(carreraService.modificarCarrera("1", carreraDTO)).thenReturn(carreraDTO);

        mockMvc.perform(put("/carreras/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carreraDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nombre").value("Ingeniería en Sistemas"))
                .andExpect(jsonPath("$.codigo").value("IS001"))
                .andExpect(jsonPath("$.departamento").value(1))
                .andExpect(jsonPath("$.cantidadCuatrimestres").value(10))
                .andExpect(jsonPath("$.materiasIds[0]").value("materia1"));
    }

    @Test
    public void testEliminarCarrera() throws Exception {
        mockMvc.perform(delete("/carreras/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testListarCarreras() throws Exception {
        when(carreraService.listarCarreras()).thenReturn(Arrays.asList(carreraDTO));

        mockMvc.perform(get("/carreras"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nombre").value("Ingeniería en Sistemas"))
                .andExpect(jsonPath("$[0].codigo").value("IS001"))
                .andExpect(jsonPath("$[0].departamento").value(1))
                .andExpect(jsonPath("$[0].cantidadCuatrimestres").value(10))
                .andExpect(jsonPath("$[0].materiasIds[0]").value("materia1"));
    }

    @Test
    public void testObtenerCarreraPorId() throws Exception {
        when(carreraService.obtenerCarreraPorId("1")).thenReturn(carreraDTO);

        mockMvc.perform(get("/carreras/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nombre").value("Ingeniería en Sistemas"))
                .andExpect(jsonPath("$.codigo").value("IS001"))
                .andExpect(jsonPath("$.departamento").value(1))
                .andExpect(jsonPath("$.cantidadCuatrimestres").value(10))
                .andExpect(jsonPath("$.materiasIds[0]").value("materia1"));
    }
}

