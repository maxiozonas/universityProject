package com.universityproject.service.implementation;

import com.universityproject.model.dto.CarreraDTO;
import com.universityproject.model.Carrera;
import com.universityproject.repository.CarreraRepository;
import com.universityproject.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarreraServiceImpl implements CarreraService {

    @Autowired
    private CarreraRepository carreraRepository;

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

    @Override
    public void eliminarCarrera(String id) {
        carreraRepository.deleteById(id);
    }

    @Override
    public List<CarreraDTO> listarCarreras() {
        List<Carrera> carreras = carreraRepository.findAll();
        return carreras.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CarreraDTO obtenerCarreraPorId(String id) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
        return mapToDTO(carrera);
    }

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

