package com.universityproject.controller;

import com.universityproject.model.dto.MateriaDTO;
import com.universityproject.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones relacionadas con Materias.
 * Proporciona endpoints para crear, modificar, eliminar y obtener Materias.
 */
@RestController
@RequestMapping("/materias")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    /**
     * Crea una nueva Materia.
     *
     * @param materiaDTO Objeto DTO con los datos de la Materia a crear.
     * @return La Materia creada en forma de DTO.
     */
    @PostMapping
    public ResponseEntity<MateriaDTO> crearMateria(@RequestBody MateriaDTO materiaDTO) {
        MateriaDTO createdMateria = materiaService.crearMateria(materiaDTO);
        return new ResponseEntity<>(createdMateria, HttpStatus.CREATED);
    }

    /**
     * Modifica una Materia existente.
     *
     * @param id         El ID de la Materia a modificar.
     * @param materiaDTO Objeto DTO con los nuevos datos de la Materia.
     * @return La Materia modificada en forma de DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MateriaDTO> modificarMateria(@PathVariable String id, @RequestBody MateriaDTO materiaDTO) {
        MateriaDTO updatedMateria = materiaService.modificarMateria(id, materiaDTO);
        return new ResponseEntity<>(updatedMateria, HttpStatus.OK);
    }

    /**
     * Elimina una Materia existente.
     *
     * @param id El ID de la Materia a eliminar.
     * @return Una respuesta vacía con código de estado 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMateria(@PathVariable String id) {
        materiaService.eliminarMateria(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Obtiene una Materia por su ID.
     *
     * @param id El ID de la Materia a obtener.
     * @return La Materia correspondiente en forma de DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MateriaDTO> ObtenerMateriaPorId(@PathVariable String id) {
        MateriaDTO materiaDTO = materiaService.ObtenerMateriaPorId(id);
        return new ResponseEntity<>(materiaDTO, HttpStatus.OK);
    }

    /**
     * Obtiene una lista de todas las Materias.
     *
     * @return Lista de MateriaDTO con todas las Materias.
     */
    @GetMapping
    public ResponseEntity<List<MateriaDTO>> ObtenerMaterias() {
        List<MateriaDTO> materias = materiaService.ObtenerMaterias();
        return new ResponseEntity<>(materias, HttpStatus.OK);
    }

    /**
     * Obtiene una lista de Materias cuyo nombre contiene una cadena específica.
     *
     * @param nombre El nombre (o parte de él) para filtrar Materias.
     * @return Lista de MateriaDTO que coinciden con el nombre filtrado.
     */
    @GetMapping("/search")
    public ResponseEntity<List<MateriaDTO>> ObtenerMateriasPorNombre(@RequestParam String nombre) {
        List<MateriaDTO> materias = materiaService.ObtenerMateriasPorNombre(nombre);
        return new ResponseEntity<>(materias, HttpStatus.OK);
    }

    /**
     * Obtiene una lista de Materias ordenadas según un criterio especificado.
     *
     * @param order El criterio de ordenación (nombre o código, ascendente o descendente).
     * @return Lista de MateriaDTO ordenadas.
     */
    @GetMapping("/order")
    public ResponseEntity<List<MateriaDTO>> ObtenerMateriasOrdenadas(@RequestParam String order) {
        List<MateriaDTO> materias = materiaService.ObtenerMateriasOrdenadas(order);
        return new ResponseEntity<>(materias, HttpStatus.OK);
    }
}

