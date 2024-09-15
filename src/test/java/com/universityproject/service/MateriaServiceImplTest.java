package com.universityproject.service;

import com.universityproject.model.Carrera;
import com.universityproject.model.Materia;
import com.universityproject.model.dto.MateriaDTO;
import com.universityproject.model.dto.CarreraSimpleDTO;
import com.universityproject.model.exception.CarreraNotFoundException;
import com.universityproject.model.exception.MateriaInvalidDataException;
import com.universityproject.model.exception.MateriaNotFoundException;
import com.universityproject.repository.CarreraRepository;
import com.universityproject.repository.MateriaRepository;
import com.universityproject.service.implementation.MateriaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MateriaServiceImplTest {

    @Mock
    private MateriaRepository materiaRepository;

    @Mock
    private CarreraRepository carreraRepository;

    @InjectMocks
    private MateriaServiceImpl materiaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearMateria() {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId("1");
        materiaDTO.setNombre("Programación I");
        materiaDTO.setAnio(1);
        materiaDTO.setCuatrimestre(1);
        materiaDTO.setCorrelativasIds(new ArrayList<>());
        CarreraSimpleDTO carreraDTO = new CarreraSimpleDTO();
        carreraDTO.setId("1");
        carreraDTO.setNombre("Ingeniería de Sistemas");
        materiaDTO.setCarrera(carreraDTO);

        Materia materia = new Materia();
        materia.setId("1");
        materia.setNombre("Programación I");
        materia.setAnio(1);
        materia.setCuatrimestre(1);
        materia.setCorrelativasIds(new ArrayList<>());
        materia.setCarreraId("1");

        Carrera carrera = new Carrera();
        carrera.setId("1");
        carrera.setNombre("Ingeniería de Sistemas");
        carrera.setMateriasIds(new ArrayList<>());

        when(carreraRepository.findById(anyString())).thenReturn(Optional.of(carrera));
        when(materiaRepository.save(any(Materia.class))).thenReturn(materia);

        MateriaDTO result = materiaService.crearMateria(materiaDTO);
        assertEquals("1", result.getId());
        assertEquals("Programación I", result.getNombre());
    }

    @Test
    public void testModificarMateria() {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId("1");
        materiaDTO.setNombre("Programación IV");
        materiaDTO.setAnio(2);
        materiaDTO.setCuatrimestre(2);
        materiaDTO.setCorrelativasIds(Collections.singletonList("2"));
        CarreraSimpleDTO carreraDTO = new CarreraSimpleDTO();
        carreraDTO.setId("2");
        carreraDTO.setNombre("Ingeniería de Software");
        materiaDTO.setCarrera(carreraDTO);

        Materia existingMateria = new Materia();
        existingMateria.setId("1");
        existingMateria.setNombre("Programación III");
        existingMateria.setAnio(1);
        existingMateria.setCuatrimestre(1);
        existingMateria.setCorrelativasIds(new ArrayList<>());
        existingMateria.setCarreraId("1");

        Materia updatedMateria = new Materia();
        updatedMateria.setId("1");
        updatedMateria.setNombre("Programación IV");
        updatedMateria.setAnio(2);
        updatedMateria.setCuatrimestre(2);
        updatedMateria.setCorrelativasIds(Collections.singletonList("2"));
        updatedMateria.setCarreraId("2");

        Carrera nuevaCarrera = new Carrera();
        nuevaCarrera.setId("2");
        nuevaCarrera.setNombre("Ingeniería de Software");
        nuevaCarrera.setMateriasIds(new ArrayList<>());

        when(materiaRepository.findById(anyString())).thenReturn(Optional.of(existingMateria));
        when(carreraRepository.findById(anyString())).thenReturn(Optional.of(nuevaCarrera));
        when(materiaRepository.save(any(Materia.class))).thenReturn(updatedMateria);

        MateriaDTO result = materiaService.modificarMateria("1", materiaDTO);
        assertEquals("1", result.getId());
        assertEquals("Programación IV", result.getNombre());
    }

    @Test
    public void testModificarMateria_MateriaNotFoundException() {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId("1");

        when(materiaRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(MateriaNotFoundException.class, () -> materiaService.modificarMateria("1", materiaDTO));
    }

    @Test
    public void testEliminarMateria() {
        Materia materia = new Materia();
        materia.setId("1");
        materia.setCarreraId("2");

        Carrera carrera = new Carrera();
        carrera.setId("2");
        carrera.setMateriasIds(new ArrayList<>(Collections.singletonList("1")));

        when(materiaRepository.findById(anyString())).thenReturn(Optional.of(materia));
        when(carreraRepository.findById(anyString())).thenReturn(Optional.of(carrera));

        materiaService.eliminarMateria("1");
        verify(materiaRepository, times(1)).delete(materia);
        verify(carreraRepository, times(1)).save(carrera);
    }

    @Test
    public void testEliminarMateria_MateriaNotFoundException() {
        when(materiaRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(MateriaNotFoundException.class, () -> materiaService.eliminarMateria("1"));
    }

    @Test
    public void testObtenerMateriaPorId() {
        Materia materia = new Materia();
        materia.setId("1");
        materia.setNombre("Programación I");

        when(materiaRepository.findById(anyString())).thenReturn(Optional.of(materia));

        MateriaDTO result = materiaService.obtenerMateriaPorId("1");
        assertEquals("1", result.getId());
        assertEquals("Programación I", result.getNombre());
    }

    @Test
    public void testObtenerMateriaPorId_MateriaNotFoundException() {
        when(materiaRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(MateriaNotFoundException.class, () -> materiaService.obtenerMateriaPorId("1"));
    }

    @Test
    public void testListarMaterias() {
        Materia materia = new Materia();
        materia.setId("1");
        materia.setNombre("Programación I");

        when(materiaRepository.findAll()).thenReturn(Collections.singletonList(materia));

        List<MateriaDTO> result = materiaService.listarMaterias();
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Programación I", result.get(0).getNombre());
    }

    @Test
    public void testObtenerMateriasPorNombre() {
        Materia materia = new Materia();
        materia.setId("1");
        materia.setNombre("Programación I");

        when(materiaRepository.findByNombreContainingIgnoreCase(anyString()))
                .thenReturn(Collections.singletonList(materia));

        List<MateriaDTO> result = materiaService.obtenerMateriasPorNombre("Programación");

        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Programación I", result.get(0).getNombre());
    }
}

