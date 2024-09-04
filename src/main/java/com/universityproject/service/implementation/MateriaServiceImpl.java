package com.universityproject.service.implementation;

import com.universityproject.model.dto.MateriaDTO;
import com.universityproject.model.Carrera;
import com.universityproject.model.Materia;
import com.universityproject.repository.CarreraRepository;
import com.universityproject.repository.MateriaRepository;
import com.universityproject.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Override
    public MateriaDTO crearMateria(MateriaDTO materiaDTO) {
        Materia materia = new Materia();
        materia.setNombre(materiaDTO.getNombre());
        materia.setAnio(materiaDTO.getAnio());
        materia.setCuatrimestre(materiaDTO.getCuatrimestre());
        materia.setCorrelativasIds(materiaDTO.getCorrelativasIds());
        materia.setCarreraId(materiaDTO.getCarreraId());

        // Guardar la Materia en la base de datos
        materia = materiaRepository.save(materia);

        // Asociar la Materia a la Carrera
        Carrera carrera = carreraRepository.findById(materiaDTO.getCarreraId())
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        // Actualizar la lista de materias de la Carrera
        List<String> materiasIds = carrera.getMateriasIds();
        if (!materiasIds.contains(materia.getId())) {
            materiasIds.add(materia.getId());
        }

        carrera = carreraRepository.save(carrera);
        System.out.println("Carrera actualizada: " + carrera); // Log para depuración

        return mapToDTO(materia);
    }

    @Override
    public MateriaDTO modificarMateria(String id, MateriaDTO materiaDTO) {
        Materia materia = materiaRepository.findById(id).orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        materia.setNombre(materiaDTO.getNombre());
        materia.setAnio(materiaDTO.getAnio());
        materia.setCuatrimestre(materiaDTO.getCuatrimestre());
        materia.setCorrelativasIds(materiaDTO.getCorrelativasIds());

        materiaRepository.save(materia);

        return mapToDTO(materia);
    }

    @Override
    public void eliminarMateria(String id) {
        Materia materia = materiaRepository.findById(id).orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        // Eliminar la referencia de la Materia de la Carrera asociada
        Carrera carrera = carreraRepository.findById(materia.getCarreraId()).orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
        carrera.getMateriasIds().remove(materia.getId());
        carreraRepository.save(carrera);

        materiaRepository.delete(materia);
    }

    @Override
    public MateriaDTO ObtenerMateriaPorId(String id) {
        Materia materia = materiaRepository.findById(id).orElseThrow(() -> new RuntimeException("Materia no encontrada"));
        return mapToDTO(materia);
    }

    @Override
    public List<MateriaDTO> ObtenerMaterias() {
        return materiaRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MateriaDTO> ObtenerMateriasPorNombre(String nombre) {
        return materiaRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MateriaDTO> ObtenerMateriasOrdenadas(String orderBy) {
        List<Materia> materias = materiaRepository.findAll();
        Comparator<Materia> comparator = switch (orderBy) {
            case "nombre_asc" -> Comparator.comparing(Materia::getNombre);
            case "nombre_desc" -> Comparator.comparing(Materia::getNombre).reversed();
            case "codigo_asc" -> Comparator.comparing(Materia::getId);
            case "codigo_desc" -> Comparator.comparing(Materia::getId).reversed();
            default -> throw new IllegalArgumentException("Parámetro de ordenación no válido");
        };

        return materias.stream()
                .sorted(comparator)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private MateriaDTO mapToDTO(Materia materia) {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId(materia.getId());
        materiaDTO.setNombre(materia.getNombre());
        materiaDTO.setAnio(materia.getAnio());
        materiaDTO.setCuatrimestre(materia.getCuatrimestre());
        materiaDTO.setCorrelativasIds(materia.getCorrelativasIds());
        materiaDTO.setCarreraId(materia.getCarreraId());

        return materiaDTO;
    }
}

