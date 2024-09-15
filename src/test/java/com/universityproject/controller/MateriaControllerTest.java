package com.universityproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.universityproject.model.dto.CarreraSimpleDTO;
import com.universityproject.model.dto.MateriaDTO;
import com.universityproject.service.MateriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MateriaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MateriaService materiaService;

    @InjectMocks
    private MateriaController materiaController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(materiaController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCrearMateria() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId("1");
        materiaDTO.setNombre("Programación III");
        materiaDTO.setAnio(2);
        materiaDTO.setCuatrimestre(1);
        materiaDTO.setCorrelativasIds(Collections.emptyList());

        CarreraSimpleDTO carreraSimpleDTO = new CarreraSimpleDTO();
        carreraSimpleDTO.setId("1");
        carreraSimpleDTO.setNombre("Ingeniería de Sistemas");
        materiaDTO.setCarrera(carreraSimpleDTO);

        when(materiaService.crearMateria(any(MateriaDTO.class))).thenReturn(materiaDTO);

        mockMvc.perform(post("/materias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(materiaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nombre").value("Programación III"));
    }


    @Test
    public void testModificarMateria() throws Exception {
        // Crear un objeto MateriaDTO y establecer sus valores utilizando setters
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId("1");
        materiaDTO.setNombre("Programación IV");
        materiaDTO.setAnio(2);
        materiaDTO.setCuatrimestre(2);
        materiaDTO.setCorrelativasIds(Collections.singletonList("2"));

        // Crear un objeto CarreraSimpleDTO y establecer sus valores utilizando setters
        CarreraSimpleDTO carreraSimpleDTO = new CarreraSimpleDTO();
        carreraSimpleDTO.setId("1");
        carreraSimpleDTO.setNombre("Ingeniería de Sistemas");
        materiaDTO.setCarrera(carreraSimpleDTO);

        // Configurar el comportamiento del mock para el servicio
        when(materiaService.modificarMateria(anyString(), any(MateriaDTO.class))).thenReturn(materiaDTO);

        // Realizar la solicitud PUT y verificar la respuesta
        mockMvc.perform(put("/materias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(materiaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nombre").value("Programación IV"));
    }


    @Test
    public void testEliminarMateria() throws Exception {
        doNothing().when(materiaService).eliminarMateria(anyString());

        mockMvc.perform(delete("/materias/1"))
                .andExpect(status().isOk());

        verify(materiaService, times(1)).eliminarMateria("1");
    }

    @Test
    public void testObtenerMateriaPorId() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId("1");
        materiaDTO.setNombre("Programación I");
        materiaDTO.setAnio(1);
        materiaDTO.setCuatrimestre(1);

        when(materiaService.obtenerMateriaPorId(anyString())).thenReturn(materiaDTO);

        mockMvc.perform(get("/materias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nombre").value("Programación I"));
    }

    @Test
    public void testListarMaterias() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId("1");
        materiaDTO.setNombre("Programación I");
        materiaDTO.setAnio(1);
        materiaDTO.setCuatrimestre(1);

        when(materiaService.listarMaterias()).thenReturn(Collections.singletonList(materiaDTO));

        mockMvc.perform(get("/materias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nombre").value("Programación I"));
    }

    @Test
    public void testObtenerMateriasPorNombre() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId("1");
        materiaDTO.setNombre("Programación I");
        materiaDTO.setAnio(1);
        materiaDTO.setCuatrimestre(1);

        when(materiaService.obtenerMateriasPorNombre(anyString())).thenReturn(Collections.singletonList(materiaDTO));

        mockMvc.perform(get("/materias/search?nombre=Programación I"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nombre").value("Programación I"));
    }
}


