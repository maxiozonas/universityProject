package com.universityproject.service;

import com.universityproject.model.dto.CarreraDTO;

import java.util.List;

public interface CarreraService {

    CarreraDTO crearCarrera(CarreraDTO carreraDto);

    CarreraDTO modificarCarrera(String id, CarreraDTO carreraDto);

    void eliminarCarrera(String id);

    List<CarreraDTO> listarCarreras();

    CarreraDTO obtenerCarreraPorId(String id);
}
