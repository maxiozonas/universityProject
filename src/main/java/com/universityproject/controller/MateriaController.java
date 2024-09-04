package com.universityproject.controller;

import com.universityproject.model.dto.MateriaDTO;
import com.universityproject.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @PostMapping
    public ResponseEntity<MateriaDTO> crearMateria(@RequestBody MateriaDTO materiaDTO) {
        MateriaDTO createdMateria = materiaService.crearMateria(materiaDTO);
        return new ResponseEntity<>(createdMateria, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaDTO> modificarMateria(@PathVariable String id, @RequestBody MateriaDTO materiaDTO) {
        MateriaDTO updatedMateria = materiaService.modificarMateria(id, materiaDTO);
        return new ResponseEntity<>(updatedMateria, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMateria(@PathVariable String id) {
        materiaService.eliminarMateria(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaDTO> ObtenerMateriaPorId(@PathVariable String id) {
        MateriaDTO materiaDTO = materiaService.ObtenerMateriaPorId(id);
        return new ResponseEntity<>(materiaDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MateriaDTO>> ObtenerMaterias() {
        List<MateriaDTO> materias = materiaService.ObtenerMaterias();
        return new ResponseEntity<>(materias, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MateriaDTO>> ObtenerMateriasPorNombre(@RequestParam String nombre) {
        List<MateriaDTO> materias = materiaService.ObtenerMateriasPorNombre(nombre);
        return new ResponseEntity<>(materias, HttpStatus.OK);
    }

    @GetMapping("/order")
    public ResponseEntity<List<MateriaDTO>> ObtenerMateriasOrdenadas(@RequestParam String order) {
        List<MateriaDTO> materias = materiaService.ObtenerMateriasOrdenadas(order);
        return new ResponseEntity<>(materias, HttpStatus.OK);
    }
}

