package com.universityproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.universityproject.model.Alumno;
import com.universityproject.model.dto.AlumnoDTO;
import com.universityproject.model.exception.AlumnoInvalidDataException;
import com.universityproject.model.exception.AlumnoNotFoundException;
import com.universityproject.model.exception.AsignaturaNotFoundException;
import com.universityproject.service.AlumnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AlumnoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AlumnoService alumnoService;

    @InjectMocks
    private AlumnoController alumnoController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(alumnoController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCrearAlumno() throws Exception {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId("1");
        alumnoDTO.setNombre("Juan");
        alumnoDTO.setApellido("Pérez");
        alumnoDTO.setDni("12345678");

        when(alumnoService.crearAlumno(any(AlumnoDTO.class))).thenReturn(alumnoDTO);

        mockMvc.perform(post("/alumnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alumnoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    public void testModificarAlumno() throws Exception {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId("1");
        alumnoDTO.setNombre("Juan");
        alumnoDTO.setApellido("Pérez");
        alumnoDTO.setDni("12345678");

        when(alumnoService.modificarAlumno(anyString(), any(AlumnoDTO.class))).thenReturn(alumnoDTO);

        mockMvc.perform(put("/alumnos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alumnoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    public void testEliminarAlumno() throws Exception {
        mockMvc.perform(delete("/alumnos/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testObtenerAlumnoPorId() throws Exception {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId("1");
        alumnoDTO.setNombre("Juan");
        alumnoDTO.setApellido("Pérez");
        alumnoDTO.setDni("12345678");

        when(alumnoService.obtenerAlumnoPorId(anyString())).thenReturn(alumnoDTO);

        mockMvc.perform(get("/alumnos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    public void testListarAlumnos() throws Exception {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId("1");
        alumnoDTO.setNombre("Juan");

        when(alumnoService.listarAlumnos()).thenReturn(Collections.singletonList(alumnoDTO));

        mockMvc.perform(get("/alumnos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }
}



