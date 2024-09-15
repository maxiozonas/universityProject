package com.universityproject.service.implementation;

import com.universityproject.model.dto.CarreraDTO;
import com.universityproject.model.Carrera;
import com.universityproject.model.Materia;
import com.universityproject.model.exception.CarreraNotFoundException;
import com.universityproject.repository.CarreraRepository;
import com.universityproject.repository.MateriaRepository;
import com.universityproject.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de alumnos, que maneja la lógica de negocio para las operaciones CRUD sobre Carreras.
 */
@Service
public class CarreraServiceImpl implements CarreraService {

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    /**
     * Crea una nueva carrera.
     *
     * @param carreraDTO El objeto DTO que contiene los detalles de la carrera a crear.
     * @return El objeto DTO de la carrera creada.
     */
    @Override
    public CarreraDTO crearCarrera(CarreraDTO carreraDTO) {
        Carrera carrera = new Carrera();
        carrera.setNombre(carreraDTO.getNombre());
        carrera.setCodigo(carreraDTO.getCodigo());
        carrera.setDepartamento(carreraDTO.getDepartamento());
        carrera.setCantidadCuatrimestres(carreraDTO.getCantidadCuatrimestres());
        carreraRepository.save(carrera);
        return mapToDTO(carrera);
    }

    /**
     * Modifica una carrera existente.
     *
     * @param id El ID de la carrera a modificar.
     * @param carreraDTO El objeto DTO que contiene los detalles actualizados de la carrera.
     * @return El objeto DTO de la carrera modificada.
     * @throws CarreraNotFoundException Si la carrera con el ID especificado no se encuentra.
     */
    @Override
    public CarreraDTO modificarCarrera(String id, CarreraDTO carreraDTO) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new CarreraNotFoundException("Carrera no encontrada"));

        carrera.setNombre(carreraDTO.getNombre());
        carrera.setCodigo(carreraDTO.getCodigo());
        carrera.setDepartamento(carreraDTO.getDepartamento());
        carrera.setCantidadCuatrimestres(carreraDTO.getCantidadCuatrimestres());

        carreraRepository.save(carrera);
        return mapToDTO(carrera);
    }

    /**
     * Elimina una carrera existente.
     *
     * @param id El ID de la carrera a eliminar.
     * @throws CarreraNotFoundException Si la carrera con el ID especificado no se encuentra.
     */
    @Override
    public void eliminarCarrera(String id) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new CarreraNotFoundException("Carrera no encontrada."));
        carreraRepository.delete(carrera);
    }

    /**
     * Lista todas las carreras.
     *
     * @return Una lista de objetos DTO que representan todas las carreras.
     */
    @Override
    public List<CarreraDTO> listarCarreras() {
        List<Carrera> carreras = carreraRepository.findAll();
        return carreras.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Obtiene los detalles de una carrera específica por su ID.
     *
     * @param id El ID de la carrera a obtener.
     * @return El objeto DTO de la carrera solicitada.
     * @throws RuntimeException Si la carrera con el ID especificado no se encuentra.
     */
    @Override
    public CarreraDTO obtenerCarreraPorId(String id) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new CarreraNotFoundException("Carrera no encontrada"));
        return mapToDTO(carrera);
    }

    /**
     * Convierte un objeto Carrera a su correspondiente DTO.
     *
     * @param carrera El objeto Carrera a convertir.
     * @return El objeto DTO correspondiente.
     */
    private CarreraDTO mapToDTO(Carrera carrera) {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setId(carrera.getId());
        carreraDTO.setNombre(carrera.getNombre());
        carreraDTO.setCodigo(carrera.getCodigo());
        carreraDTO.setDepartamento(carrera.getDepartamento());
        carreraDTO.setCantidadCuatrimestres(carrera.getCantidadCuatrimestres());

        // Obtener los nombres de las materias desde sus IDs
        List<String> materiasNombres = carrera.getMateriasIds().stream()
                .filter(Objects::nonNull)
                .map(id -> materiaRepository.findById(id)
                        .map(Materia::getNombre)
                        .orElse("Materia no encontrada"))
                .collect(Collectors.toList());

        carreraDTO.setMateriasNombres(materiasNombres);
        return carreraDTO;
    }
}


