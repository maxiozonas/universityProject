package com.universityproject.controller;

import com.universityproject.dto.MateriaDto;
import com.universityproject.model.Materia;
import com.universityproject.service.MateriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @PostMapping
    public ResponseEntity<Materia> crearMateria(@RequestBody MateriaDto materiaDto) {
        Materia materia = materiaService.crearMateria(materiaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(materia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Materia> actualizarMateria(@PathVariable String id, @RequestBody MateriaDto materiaDto) {
        Materia materia = materiaService.actualizarMateria(id, materiaDto);
        return ResponseEntity.ok(materia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMateria(@PathVariable String id) {
        materiaService.eliminarMateria(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Materia>> obtenerMateriasPorNombre(@RequestParam(required = false) String nombre,
                                                                  @RequestParam(required = false, defaultValue = "nombre_asc") String order) {
        List<Materia> materias = materiaService.obtenerMateriasPorNombre(nombre, order);
        return ResponseEntity.ok(materias);
    }
}
