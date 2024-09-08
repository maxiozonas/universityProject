package com.universityproject.service.implementation;

import com.universityproject.model.dto.CarreraDTO;
import com.universityproject.model.Carrera;
import com.universityproject.repository.CarreraRepository;
import com.universityproject.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para gestionar operaciones relacionadas con las carreras.
 * Proporciona métodos para crear, modificar, eliminar, listar y obtener carreras.
 */
@Service
public class CarreraServiceImpl implements CarreraService {

    @Autowired
    private CarreraRepository carreraRepository;

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
        Carrera carreraGuardada = carreraRepository.save(carrera);
        return mapToDTO(carreraGuardada);
    }

    /**
     * Modifica una carrera existente.
     *
     * @param id El ID de la carrera a modificar.
     * @param carreraDTO El objeto DTO que contiene los detalles actualizados de la carrera.
     * @return El objeto DTO de la carrera modificada.
     * @throws RuntimeException Si la carrera con el ID especificado no se encuentra.
     */
    @Override
    public CarreraDTO modificarCarrera(String id, CarreraDTO carreraDTO) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        carrera.setNombre(carreraDTO.getNombre());
        carrera.setCodigo(carreraDTO.getCodigo());
        carrera.setDepartamento(carreraDTO.getDepartamento());
        carrera.setCantidadCuatrimestres(carreraDTO.getCantidadCuatrimestres());

        Carrera carreraActualizada = carreraRepository.save(carrera);
        return mapToDTO(carreraActualizada);
    }

    /**
     * Elimina una carrera existente.
     *
     * @param id El ID de la carrera a eliminar.
     */
    @Override
    public void eliminarCarrera(String id) {
        carreraRepository.deleteById(id);
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
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
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
        carreraDTO.setMateriasIds(carrera.getMateriasIds());
        return carreraDTO;
    }
}


