package com.universityproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.universityproject.model.dto.MateriaDTO;
import com.universityproject.service.MateriaService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MateriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MateriaService materiaService;

    @Autowired
    private ObjectMapper objectMapper;

    private MateriaDTO materiaDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        materiaDTO = new MateriaDTO();
        materiaDTO.setId("1");
        materiaDTO.setNombre("Programación I");
        materiaDTO.setAnio(1);
        materiaDTO.setCuatrimestre(1);
        materiaDTO.setCorrelativasIds(Collections.emptyList());
        materiaDTO.setCarreraId("1");
    }

    @Test
    public void testCrearMateria() throws Exception {
        when(materiaService.crearMateria(materiaDTO)).thenReturn(materiaDTO);

        mockMvc.perform(post("/materias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(materiaDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Programación I"));
    }

    @Test
    public void testModificarMateria() throws Exception {
        when(materiaService.modificarMateria("1", materiaDTO)).thenReturn(materiaDTO);

        mockMvc.perform(put("/materias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(materiaDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Programación I"));
    }

    @Test
    public void testEliminarMateria() throws Exception {
        mockMvc.perform(delete("/materias/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testObtenerMateriaPorId() throws Exception {
        when(materiaService.ObtenerMateriaPorId("1")).thenReturn(materiaDTO);

        mockMvc.perform(get("/materias/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Programación I"));
    }

    @Test
    public void testObtenerMaterias() throws Exception {
        when(materiaService.ObtenerMaterias()).thenReturn(Collections.singletonList(materiaDTO));

        mockMvc.perform(get("/materias"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value("Programación I"));
    }

    @Test
    public void testObtenerMateriasPorNombre() throws Exception {
        when(materiaService.ObtenerMateriasPorNombre("Programación")).thenReturn(Collections.singletonList(materiaDTO));

        mockMvc.perform(get("/materias/search")
                        .param("nombre", "Programación"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value("Programación I"));
    }

    @Test
    public void testObtenerMateriasOrdenadas() throws Exception {
        when(materiaService.ObtenerMateriasOrdenadas("nombre_asc")).thenReturn(Collections.singletonList(materiaDTO));

        mockMvc.perform(get("/materias/order")
                        .param("order", "nombre_asc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value("Programación I"));
    }
}

