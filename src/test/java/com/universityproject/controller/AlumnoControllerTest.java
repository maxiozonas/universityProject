package com.universityproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.universityproject.model.EstadoAsignatura;
import com.universityproject.model.dto.AlumnoDTO;
import com.universityproject.service.AlumnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AlumnoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlumnoService alumnoService;

    @Autowired
    private ObjectMapper objectMapper;

    private AlumnoDTO alumnoDTO;

    @BeforeEach
    void setUp() {
        alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId("1");
        alumnoDTO.setNombre("Juan");
        alumnoDTO.setApellido("Pérez");
        alumnoDTO.setDni("12345678");
        // Agrega aquí asignaturas si es necesario
    }

    @Test
    void testCrearAlumno() throws Exception {
        when(alumnoService.crearAlumno(any(AlumnoDTO.class))).thenReturn(alumnoDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/alumnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alumnoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(alumnoDTO.getId()))
                .andExpect(jsonPath("$.nombre").value(alumnoDTO.getNombre()))
                .andExpect(jsonPath("$.apellido").value(alumnoDTO.getApellido()))
                .andExpect(jsonPath("$.dni").value(alumnoDTO.getDni()));
    }

    @Test
    void testModificarAlumno() throws Exception {
        when(alumnoService.modificarAlumno(anyString(), any(AlumnoDTO.class))).thenReturn(alumnoDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/alumnos/{idAlumno}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alumnoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(alumnoDTO.getId()))
                .andExpect(jsonPath("$.nombre").value(alumnoDTO.getNombre()))
                .andExpect(jsonPath("$.apellido").value(alumnoDTO.getApellido()))
                .andExpect(jsonPath("$.dni").value(alumnoDTO.getDni()));
    }

    @Test
    void testEliminarAlumno() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/alumnos/{idAlumno}", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerAlumnoPorId() throws Exception {
        when(alumnoService.obtenerAlumnoPorId(anyString())).thenReturn(alumnoDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/alumnos/{idAlumno}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(alumnoDTO.getId()))
                .andExpect(jsonPath("$.nombre").value(alumnoDTO.getNombre()))
                .andExpect(jsonPath("$.apellido").value(alumnoDTO.getApellido()))
                .andExpect(jsonPath("$.dni").value(alumnoDTO.getDni()));
    }

    @Test
    void testListarAlumnos() throws Exception {
        when(alumnoService.listarAlumnos()).thenReturn(List.of(alumnoDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/alumnos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(alumnoDTO.getId()))
                .andExpect(jsonPath("$[0].nombre").value(alumnoDTO.getNombre()))
                .andExpect(jsonPath("$[0].apellido").value(alumnoDTO.getApellido()))
                .andExpect(jsonPath("$[0].dni").value(alumnoDTO.getDni()));
    }

    @Test
    void testActualizarEstadoAsignatura() throws Exception {
        when(alumnoService.actualizarEstadoAsignatura(anyString(), anyString(), any())).thenReturn(alumnoDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/alumnos/{idAlumno}/asignatura/{idAsignatura}", "1", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(EstadoAsignatura.APROBADA)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(alumnoDTO.getId()))
                .andExpect(jsonPath("$.nombre").value(alumnoDTO.getNombre()))
                .andExpect(jsonPath("$.apellido").value(alumnoDTO.getApellido()))
                .andExpect(jsonPath("$.dni").value(alumnoDTO.getDni()));
    }
}

