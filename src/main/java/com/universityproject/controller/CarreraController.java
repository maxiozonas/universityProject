package com.universityproject.controller;

import com.universityproject.model.dto.CarreraDTO;
import com.universityproject.model.exception.AlumnoInvalidDataException;
import com.universityproject.model.exception.CarreraInvalidDataException;
import com.universityproject.model.exception.CarreraNotFoundException;
import com.universityproject.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para manejar las operaciones relacionadas con las carreras.
 */
@RestController
@RequestMapping("/carreras")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    /**
     * Crea una nueva carrera.
     *
     * @param carreraDTO El objeto DTO que contiene los detalles de la carrera a crear.
     * @return El objeto DTO de la carrera creada.
     */
    @PostMapping
    public CarreraDTO crearCarrera(@RequestBody CarreraDTO carreraDTO) {
        return carreraService.crearCarrera(carreraDTO);
    }

    /**
     * Modifica una carrera existente.
     *
     * @param idCarrera El ID de la carrera a modificar.
     * @param carreraDTO El objeto DTO que contiene los detalles actualizados de la carrera.
     * @return El objeto DTO de la carrera modificada.
     */
    @PutMapping("/{idCarrera}")
    public CarreraDTO modificarCarrera(@PathVariable String idCarrera, @RequestBody CarreraDTO carreraDTO) {
        return carreraService.modificarCarrera(idCarrera, carreraDTO);
    }

    /**
     * Elimina una carrera existente.
     *
     * @param idCarrera El ID de la carrera a eliminar.
     */
    @DeleteMapping("/{idCarrera}")
    public void eliminarCarrera(@PathVariable String idCarrera) {
        carreraService.eliminarCarrera(idCarrera);
    }

    /**
     * Lista todas las carreras.
     *
     * @return Una lista de objetos DTO que representan todas las carreras.
     */
    @GetMapping
    public List<CarreraDTO> listarCarreras() {
        return carreraService.listarCarreras();
    }

    /**
     * Obtiene los detalles de una carrera específica por su ID.
     *
     * @param idCarrera El ID de la carrera a obtener.
     * @return El objeto DTO de la carrera solicitada.
     */
    @GetMapping("/{idCarrera}")
    public CarreraDTO obtenerCarreraPorId(@PathVariable String idCarrera) {
        return carreraService.obtenerCarreraPorId(idCarrera);
    }
}


