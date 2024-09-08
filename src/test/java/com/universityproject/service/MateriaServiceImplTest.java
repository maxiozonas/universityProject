package com.universityproject.service;

import com.universityproject.model.Carrera;
import com.universityproject.model.Materia;
import com.universityproject.model.dto.MateriaDTO;
import com.universityproject.repository.CarreraRepository;
import com.universityproject.repository.MateriaRepository;
import com.universityproject.service.implementation.MateriaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MateriaServiceImplTest {

    @InjectMocks
    private MateriaServiceImpl materiaService;

    @Mock
    private MateriaRepository materiaRepository;

    @Mock
    private CarreraRepository carreraRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearMateria() {
        // Datos de prueba
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNombre("Programación III");
        materiaDTO.setAnio(2);
        materiaDTO.setCuatrimestre(1);
        materiaDTO.setCarreraId("1");

        Carrera carrera = new Carrera();
        carrera.setId("1");
        carrera.setMateriasIds(List.of());

        Materia materiaGuardada = new Materia();
        materiaGuardada.setId("123");
        materiaGuardada.setNombre("Programación III");

        // Mocks
        when(materiaRepository.save(any(Materia.class))).thenReturn(materiaGuardada);
        when(carreraRepository.findById(materiaDTO.getCarreraId())).thenReturn(Optional.of(carrera));
        when(carreraRepository.save(any(Carrera.class))).thenReturn(carrera);

        // Llamada al servicio
        MateriaDTO resultado = materiaService.crearMateria(materiaDTO);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals("Programación III", resultado.getNombre());
        assertEquals("123", resultado.getId());

        verify(materiaRepository, times(1)).save(any(Materia.class));
        verify(carreraRepository, times(1)).findById(materiaDTO.getCarreraId());
        verify(carreraRepository, times(1)).save(any(Carrera.class));
    }

    @Test
    void testModificarMateria() {
        // Datos de prueba
        String idMateria = "123";
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNombre("Programación Avanzada");
        materiaDTO.setAnio(2);
        materiaDTO.setCuatrimestre(2);

        Materia materiaExistente = new Materia();
        materiaExistente.setId(idMateria);
        materiaExistente.setNombre("Programación III");

        // Mocks
        when(materiaRepository.findById(idMateria)).thenReturn(Optional.of(materiaExistente));
        when(materiaRepository.save(any(Materia.class))).thenReturn(materiaExistente);

        // Llamada al servicio
        MateriaDTO resultado = materiaService.modificarMateria(idMateria, materiaDTO);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals("Programación Avanzada", resultado.getNombre());
        verify(materiaRepository, times(1)).findById(idMateria);
        verify(materiaRepository, times(1)).save(any(Materia.class));
    }

    @Test
    void testEliminarMateria() {
        // Datos de prueba
        String idMateria = "123";
        Materia materia = new Materia();
        materia.setId(idMateria);
        materia.setCarreraId("1");

        Carrera carrera = new Carrera();
        carrera.setId("1");
        carrera.setMateriasIds(List.of(idMateria));

        // Mocks
        when(materiaRepository.findById(idMateria)).thenReturn(Optional.of(materia));
        when(carreraRepository.findById("1")).thenReturn(Optional.of(carrera));

        // Llamada al servicio
        materiaService.eliminarMateria(idMateria);

        // Verificaciones
        verify(materiaRepository, times(1)).findById(idMateria);
        verify(carreraRepository, times(1)).findById("1");
        verify(materiaRepository, times(1)).delete(materia);
        verify(carreraRepository, times(1)).save(any(Carrera.class));
    }

    @Test
    void testObtenerMateriaPorId() {
        // Datos de prueba
        String idMateria = "123";
        Materia materia = new Materia();
        materia.setId(idMateria);
        materia.setNombre("Programación I");

        // Mocks
        when(materiaRepository.findById(idMateria)).thenReturn(Optional.of(materia));

        // Llamada al servicio
        MateriaDTO resultado = materiaService.ObtenerMateriaPorId(idMateria);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals("Programación I", resultado.getNombre());
        verify(materiaRepository, times(1)).findById(idMateria);
    }

    @Test
    void testObtenerMaterias() {
        // Datos de prueba
        Materia materia1 = new Materia();
        materia1.setId("1");
        materia1.setNombre("Programación I");

        Materia materia2 = new Materia();
        materia2.setId("2");
        materia2.setNombre("Programación II");

        // Mocks
        when(materiaRepository.findAll()).thenReturn(List.of(materia1, materia2));

        // Llamada al servicio
        List<MateriaDTO> resultado = materiaService.ObtenerMaterias();

        // Verificaciones
        assertEquals(2, resultado.size());
        assertEquals("Programación I", resultado.get(0).getNombre());
        assertEquals("Programación II", resultado.get(1).getNombre());
        verify(materiaRepository, times(1)).findAll();
    }

    @Test
    void testObtenerMateriasPorNombre() {
        // Datos de prueba
        Materia materia = new Materia();
        materia.setId("1");
        materia.setNombre("Programación");

        // Mocks
        when(materiaRepository.findByNombreContainingIgnoreCase("Programación")).thenReturn(List.of(materia));

        // Llamada al servicio
        List<MateriaDTO> resultado = materiaService.ObtenerMateriasPorNombre("Programación");

        // Verificaciones
        assertEquals(1, resultado.size());
        assertEquals("Programación", resultado.get(0).getNombre());
        verify(materiaRepository, times(1)).findByNombreContainingIgnoreCase("Programación");
    }

    @Test
    void testObtenerMateriasOrdenadas() {
        // Datos de prueba
        Materia materia1 = new Materia();
        materia1.setId("1");
        materia1.setNombre("Algoritmos");

        Materia materia2 = new Materia();
        materia2.setId("2");
        materia2.setNombre("Programación");

        // Mocks
        when(materiaRepository.findAll()).thenReturn(List.of(materia1, materia2));

        // Llamada al servicio (orden ascendente por nombre)
        List<MateriaDTO> resultadoAsc = materiaService.ObtenerMateriasOrdenadas("nombre_asc");

        // Verificaciones ascendente
        assertEquals(2, resultadoAsc.size());
        assertEquals("Algoritmos", resultadoAsc.get(0).getNombre());
        assertEquals("Programación", resultadoAsc.get(1).getNombre());

        // Llamada al servicio (orden descendente por nombre)
        List<MateriaDTO> resultadoDesc = materiaService.ObtenerMateriasOrdenadas("nombre_desc");

        // Verificaciones descendente
        assertEquals(2, resultadoDesc.size());
        assertEquals("Programación", resultadoDesc.get(0).getNombre());
        assertEquals("Algoritmos", resultadoDesc.get(1).getNombre());

        verify(materiaRepository, times(2)).findAll();
    }
}

