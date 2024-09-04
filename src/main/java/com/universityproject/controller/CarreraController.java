package com.universityproject.controller;

import com.universityproject.model.dto.CarreraDTO;
import com.universityproject.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carreras")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    @PostMapping
    public CarreraDTO crearCarrera(@RequestBody CarreraDTO carreraDTO) {
        return carreraService.crearCarrera(carreraDTO);
    }

    @PutMapping("/{idCarrera}")
    public CarreraDTO modificarCarrera(@PathVariable String idCarrera, @RequestBody CarreraDTO carreraDTO) {
        return carreraService.modificarCarrera(idCarrera, carreraDTO);
    }

    @DeleteMapping("/{idCarrera}")
    public void eliminarCarrera(@PathVariable String idCarrera) {
        carreraService.eliminarCarrera(idCarrera);
    }

    @GetMapping
    public List<CarreraDTO> listarCarreras() {
        return carreraService.listarCarreras();
    }

    @GetMapping("/{idCarrera}")
    public CarreraDTO obtenerCarreraPorId(@PathVariable String idCarrera) {
        return carreraService.obtenerCarreraPorId(idCarrera);
    }
}

