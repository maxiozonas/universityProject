package com.universityproject.controller;

import com.universityproject.model.dto.MateriaDTO;
import com.universityproject.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controlador para manejar las operaciones relacionadas con Materia.
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
    public MateriaDTO crearMateria(@Valid @RequestBody MateriaDTO materiaDTO) {
        return materiaService.crearMateria(materiaDTO);
    }

    /**
     * Modifica una Materia existente.
     *
     * @param idMateria  El ID de la Materia a modificar.
     * @param materiaDTO Objeto DTO con los nuevos datos de la Materia.
     * @return La Materia modificada en forma de DTO.
     */
    @PutMapping("/{idMateria}")
    public MateriaDTO modificarMateria(@PathVariable String idMateria, @Valid @RequestBody MateriaDTO materiaDTO) {
        return materiaService.modificarMateria(idMateria, materiaDTO);
    }

    /**
     * Elimina una Materia existente.
     *
     * @param idMateria El ID de la Materia a eliminar.
     */
    @DeleteMapping("/{idMateria}")
    public void eliminarMateria(@PathVariable String idMateria) {
        materiaService.eliminarMateria(idMateria);
    }

    /**
     * Obtiene una Materia por su ID.
     *
     * @param idMateria El ID de la Materia a obtener.
     * @return La Materia correspondiente en forma de DTO.
     */
    @GetMapping("/{idMateria}")
    public MateriaDTO ObtenerMateriaPorId(@PathVariable String idMateria) {
        return materiaService.obtenerMateriaPorId(idMateria);
    }

    /**
     * Obtiene una lista de todas las Materias.
     *
     * @return Lista de MateriaDTO con todas las Materias.
     */
    @GetMapping
    public List<MateriaDTO> ObtenerMaterias() {
        return materiaService.listarMaterias();
    }

    /**
     * Obtiene una lista de Materias cuyo nombre contiene una cadena específica.
     *
     * @param nombre El nombre (o parte de él) para filtrar Materias.
     * @return Lista de MateriaDTO que coinciden con el nombre filtrado.
     */
    @GetMapping("/search")
    public List<MateriaDTO> ObtenerMateriasPorNombre(@RequestParam String nombre) {
        return materiaService.obtenerMateriasPorNombre(nombre);
    }

    /**
     * Obtiene una lista de Materias ordenadas según un criterio especificado.
     *
     * @param order El criterio de ordenación (nombre o código, ascendente o descendente).
     * @return Lista de MateriaDTO ordenadas.
     */
    @GetMapping("/order")
    public List<MateriaDTO> ObtenerMateriasOrdenadas(@RequestParam String order) {
        return materiaService.obtenerMateriasOrdenadas(order);
    }
}
