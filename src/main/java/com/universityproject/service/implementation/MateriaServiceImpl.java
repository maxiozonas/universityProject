package com.universityproject.service.implementation;

import com.universityproject.model.dto.CarreraSimpleDTO;
import com.universityproject.model.dto.CorrelativaSimpleDTO;
import com.universityproject.model.dto.MateriaDTO;
import com.universityproject.model.Carrera;
import com.universityproject.model.Materia;
import com.universityproject.model.exception.CarreraInvalidDataException;
import com.universityproject.model.exception.CarreraNotFoundException;
import com.universityproject.model.exception.MateriaInvalidDataException;
import com.universityproject.model.exception.MateriaNotFoundException;
import com.universityproject.repository.CarreraRepository;
import com.universityproject.repository.MateriaRepository;
import com.universityproject.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del servicio MateriaService para gestionar las operaciones CRUD de Materia.
 */
@Service
public class MateriaServiceImpl implements MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    /**
     * Crea una nueva Materia.
     *
     * @param materiaDTO Objeto DTO que contiene los datos de la Materia a crear.
     * @return La Materia creada en forma de DTO.
     */
    @Override
    public MateriaDTO crearMateria(MateriaDTO materiaDTO) {
        try {
            Materia materia = new Materia();
            materia.setNombre(materiaDTO.getNombre());
            materia.setAnio(materiaDTO.getAnio());
            materia.setCuatrimestre(materiaDTO.getCuatrimestre());
            materia.setCorrelativasIds(materiaDTO.getCorrelativasIds());
            materia.setCarreraId(materiaDTO.getCarrera().getId());

            // Guardar materia
            materia = materiaRepository.save(materia);

            // Asociar la materia con la carrera correspondiente
            Carrera carrera = carreraRepository.findById(materiaDTO.getCarrera().getId())
                    .orElseThrow(() -> new CarreraNotFoundException("Carrera no encontrada"));
            List<String> materiasIds = Optional.ofNullable(carrera.getMateriasIds())
                    .map(ArrayList::new)
                    .orElse(new ArrayList<>());
            materiasIds.add(materia.getId());
            carrera.setMateriasIds(materiasIds);
            carreraRepository.save(carrera);

            return mapToDTO(materia);
        } catch (Exception e) {
            throw new MateriaInvalidDataException("Error al crear materia: " + e.getMessage());
        }
    }

    /**
     * Modifica una Materia existente.
     *
     * @param id         El ID de la Materia a modificar.
     * @param materiaDTO Objeto DTO con los nuevos datos de la Materia.
     * @return La Materia modificada en forma de DTO.
     */
    @Override
    public MateriaDTO modificarMateria(String id, MateriaDTO materiaDTO) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new MateriaNotFoundException("Materia no encontrada"));

        // Actualizar los campos de la materia
        materia.setNombre(materiaDTO.getNombre());
        materia.setAnio(materiaDTO.getAnio());
        materia.setCuatrimestre(materiaDTO.getCuatrimestre());
        materia.setCorrelativasIds(materiaDTO.getCorrelativasIds());

        // Verificar si la carrera cambió y actualizar la referencia
        if (!materia.getCarreraId().equals(materiaDTO.getCarrera().getId())) {
            Carrera nuevaCarrera = carreraRepository.findById(materiaDTO.getCarrera().getId())
                    .orElseThrow(() -> new MateriaInvalidDataException("Carrera no encontrada"));
            List<String> materiasIds = nuevaCarrera.getMateriasIds();
            materiasIds.add(materia.getId());
            nuevaCarrera.setMateriasIds(materiasIds);
            carreraRepository.save(nuevaCarrera);
        }

        materiaRepository.save(materia);
        return mapToDTO(materia);
    }


    /**
     * Elimina una Materia y la desasocia de su Carrera correspondiente.
     *
     * @param id El ID de la Materia a eliminar.
     */
    @Override
    public void eliminarMateria(String id) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new MateriaNotFoundException("Materia no encontrada"));

        Carrera carrera = carreraRepository.findById(materia.getCarreraId())
                .orElseThrow(() -> new CarreraInvalidDataException("Carrera no encontrada"));

        List<String> materiasIds = Optional.ofNullable(carrera.getMateriasIds())
                .map(ArrayList::new)
                .orElse(new ArrayList<>());
        materiasIds.remove(materia.getId());
        carrera.setMateriasIds(materiasIds);
        carreraRepository.save(carrera);

        materiaRepository.delete(materia);
    }

    /**
     * Obtiene una Materia por su ID.
     *
     * @param id El ID de la Materia a obtener.
     * @return La Materia correspondiente en forma de DTO.
     */
    @Override
    public MateriaDTO obtenerMateriaPorId(String id) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new MateriaNotFoundException("Materia no encontrada"));

        return mapToDTO(materia);
    }

    /**
     * Obtiene una lista de todas las Materias.
     *
     * @return Lista de MateriaDTO con todas las Materias.
     */
    @Override
    public List<MateriaDTO> listarMaterias() {
        List<Materia> materias = materiaRepository.findAll();
        return materias.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Obtiene una lista de Materias cuyo nombre contiene una cadena específica (filtro por nombre).
     *
     * @param nombre El nombre (o parte de él) para filtrar Materias.
     * @return Lista de MateriaDTO que coinciden con el nombre filtrado.
     */
    @Override
    public List<MateriaDTO> obtenerMateriasPorNombre(String nombre) {
        return materiaRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una lista de Materias ordenadas según un criterio especificado.
     *
     * @param orderBy El criterio de ordenación (nombre o código, ascendente o descendente).
     * @return Lista de MateriaDTO ordenadas.
     */
    @Override
    public List<MateriaDTO> obtenerMateriasOrdenadas(String orderBy) {
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

    /**
     * Convierte una entidad Materia en un DTO para la transferencia de datos.
     *
     * @param materia La entidad Materia a convertir.
     * @return El MateriaDTO equivalente.
     */
    private MateriaDTO mapToDTO(Materia materia) {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId(materia.getId());
        materiaDTO.setNombre(materia.getNombre());
        materiaDTO.setAnio(materia.getAnio());
        materiaDTO.setCuatrimestre(materia.getCuatrimestre());

        // Convertir correlativas
        List<CorrelativaSimpleDTO> correlativasNombres = materia.getCorrelativasIds().stream()
                .map(correlativaId -> materiaRepository.findById(correlativaId)
                        .map(correlativa -> {
                            CorrelativaSimpleDTO correlativaDTO = new CorrelativaSimpleDTO();
                            correlativaDTO.setId(correlativa.getId());
                            correlativaDTO.setNombre(correlativa.getNombre());
                            return correlativaDTO;
                        })
                        .orElse(null))
                .filter(correlativa -> correlativa != null)
                .collect(Collectors.toList());

        materiaDTO.setCorrelativas(correlativasNombres);

        // Convertir carrera
        CarreraSimpleDTO carreraDTO = carreraRepository.findById(materia.getCarreraId())
                .map(carrera -> {
                    CarreraSimpleDTO carreraSimpleDTO = new CarreraSimpleDTO();
                    carreraSimpleDTO.setId(carrera.getId());
                    carreraSimpleDTO.setNombre(carrera.getNombre());
                    return carreraSimpleDTO;
                })
                .orElse(null);

        materiaDTO.setCarrera(carreraDTO);

        return materiaDTO;
    }
}


