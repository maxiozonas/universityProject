package com.universityproject.service.Implementation;

import com.universityproject.dto.MateriaDto;
import com.universityproject.model.Materia;
import com.universityproject.repository.MateriaRepository;
import com.universityproject.service.MateriaService;
import com.universityproject.service.exception.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaServiceImpl implements MateriaService {

    private final MateriaRepository materiaRepository;

    public MateriaServiceImpl(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    @Override
    public Materia crearMateria(MateriaDto materiaDto) {
        Materia materia = new Materia();
        materia.setNombre(materiaDto.getNombre());
        materia.setAnio(materiaDto.getAnio());
        materia.setCuatrimestre(materiaDto.getCuatrimestre());
        materia.setCorrelativas(materiaDto.getCorrelativas());
        return materiaRepository.save(materia);
    }

    @Override
    public Materia actualizarMateria(String id, MateriaDto materiaDto) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada"));
        materia.setNombre(materiaDto.getNombre());
        materia.setAnio(materiaDto.getAnio());
        materia.setCuatrimestre(materiaDto.getCuatrimestre());
        materia.setCorrelativas(materiaDto.getCorrelativas());
        return materiaRepository.save(materia);
    }

    @Override
    public void eliminarMateria(String id) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada"));
        materiaRepository.delete(materia);
    }

    @Override
    public List<Materia> obtenerMateriasPorNombre(String nombre, String order) {
        if (nombre != null) {
            return materiaRepository.findByNombreContainingIgnoreCase(nombre, this.getSort(order));
        } else {
            return materiaRepository.findAll(this.getSort(order));
        }
    }

    private Sort getSort(String order) {
        if (order != null) {
            switch (order) {
                case "nombre_asc":
                    return Sort.by(Sort.Direction.ASC, "nombre");
                case "nombre_desc":
                    return Sort.by(Sort.Direction.DESC, "nombre");
                case "anio_asc":
                    return Sort.by(Sort.Direction.ASC, "anio");
                case "anio_desc":
                    return Sort.by(Sort.Direction.DESC, "anio");
                default:
                    return Sort.by(Sort.Direction.ASC, "nombre");
            }
        } else {
            return Sort.by(Sort.Direction.ASC, "nombre");
        }
    }
}
