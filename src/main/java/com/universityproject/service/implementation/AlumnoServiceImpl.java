package com.universityproject.service.implementation;

import com.universityproject.model.Alumno;
import com.universityproject.model.Asignatura;
import com.universityproject.model.EstadoAsignatura;
import com.universityproject.model.dto.AlumnoDTO;
import com.universityproject.model.dto.AsignaturaDTO;
import com.universityproject.repository.AlumnoRepository;
import com.universityproject.repository.MateriaRepository;
import com.universityproject.service.AlumnoService;
import com.universityproject.service.exception.MateriaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de alumnos, que maneja la lógica de negocio para las operaciones CRUD sobre alumnos.
 */
@Service
public class AlumnoServiceImpl implements AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    /**
     * Crea un nuevo alumno en el sistema.
     * @param alumnoDTO DTO que contiene los datos del alumno a crear.
     * @return El DTO del alumno recién creado.
     */
    @Override
    public AlumnoDTO crearAlumno(AlumnoDTO alumnoDTO) {
        Alumno alumno = new Alumno();
        alumno.setNombre(alumnoDTO.getNombre());
        alumno.setApellido(alumnoDTO.getApellido());
        alumno.setDni(alumnoDTO.getDni());

        // Asigna el estado inicial de NO_CURSADA a cada asignatura utilizando materias existentes
        List<Asignatura> asignaturas = alumnoDTO.getAsignaturas().stream()
                .map(asignaturaDTO -> {
                    Asignatura asignatura = new Asignatura();
                    String materiaId = materiaRepository.findByNombre(asignaturaDTO.getMateriaNombre())
                            .orElseThrow(() -> new MateriaNotFoundException("Materia no encontrada"))
                            .getId();

                    asignatura.setMateriaId(materiaId);
                    asignatura.setEstadoAsignatura(EstadoAsignatura.NO_CURSADA); // Estado predeterminado
                    return asignatura;
                }).collect(Collectors.toList());

        alumno.setAsignaturas(asignaturas);
        alumno = alumnoRepository.save(alumno);

        return mapToDTO(alumno);
    }

    /**
     * Modifica un alumno existente.
     * @param id El ID del alumno a modificar.
     * @param alumnoDTO DTO con los nuevos datos del alumno.
     * @return El DTO del alumno modificado.
     */
    @Override
    public AlumnoDTO modificarAlumno(String id, AlumnoDTO alumnoDTO) {
        Alumno alumno = alumnoRepository.findById(id).orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        alumno.setNombre(alumnoDTO.getNombre());
        alumno.setApellido(alumnoDTO.getApellido());
        alumno.setDni(alumnoDTO.getDni());

        alumnoRepository.save(alumno);
        return mapToDTO(alumno);
    }

    /**
     * Elimina un alumno del sistema.
     * @param id El ID del alumno a eliminar.
     */
    @Override
    public void eliminarAlumno(String id) {
        Alumno alumno = alumnoRepository.findById(id).orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        alumnoRepository.delete(alumno);
    }

    /**
     * Obtiene un alumno por su ID.
     * @param id El ID del alumno.
     * @return El DTO del alumno encontrado.
     */
    @Override
    public AlumnoDTO obtenerAlumnoPorId(String id) {
        Alumno alumno = alumnoRepository.findById(id).orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        return mapToDTO(alumno);
    }

    /**
     * Lista todos los alumnos registrados en el sistema.
     * @return Lista de DTOs de todos los alumnos.
     */
    @Override
    public List<AlumnoDTO> listarAlumnos() {
        return alumnoRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Actualiza el estado de una asignatura para un alumno.
     * @param idAlumno El ID del alumno.
     * @param idAsignatura El ID de la asignatura.
     * @param estado El nuevo estado de la asignatura.
     * @return El DTO del alumno con el estado de la asignatura actualizado.
     */
    @Override
    public AlumnoDTO actualizarEstadoAsignatura(String idAlumno, String idAsignatura, EstadoAsignatura estado) {
        Alumno alumno = alumnoRepository.findById(idAlumno).orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        // Actualiza el estado de la asignatura correspondiente
        alumno.getAsignaturas().stream()
                .filter(asignatura -> asignatura.getMateriaId().equals(idAsignatura))
                .forEach(asignatura -> asignatura.setEstadoAsignatura(estado));

        alumnoRepository.save(alumno);
        return mapToDTO(alumno);
    }

    /**
     * Convierte un objeto Alumno en un objeto AlumnoDTO.
     * @param alumno El alumno a convertir.
     * @return El DTO del alumno.
     */
    private AlumnoDTO mapToDTO(Alumno alumno) {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId(alumno.getId());
        alumnoDTO.setNombre(alumno.getNombre());
        alumnoDTO.setApellido(alumno.getApellido());
        alumnoDTO.setDni(alumno.getDni());

        // Mapea las asignaturas del alumno a AsignaturaDTO
        List<AsignaturaDTO> asignaturasDTO = alumno.getAsignaturas().stream().map(asignatura -> {
            AsignaturaDTO asignaturaDTO = new AsignaturaDTO();
            asignaturaDTO.setMateriaNombre(materiaRepository.findById(asignatura.getMateriaId())
                    .orElseThrow(() -> new RuntimeException("Materia no encontrada"))
                    .getNombre());
            asignaturaDTO.setEstadoAsignatura(asignatura.getEstadoAsignatura());
            return asignaturaDTO;
        }).collect(Collectors.toList());

        alumnoDTO.setAsignaturas(asignaturasDTO);
        return alumnoDTO;
    }
}


