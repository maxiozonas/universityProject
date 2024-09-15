package com.universityproject.service.implementation;

import com.universityproject.model.Alumno;
import com.universityproject.model.Asignatura;
import com.universityproject.model.EstadoAsignatura;
import com.universityproject.model.dto.AlumnoDTO;
import com.universityproject.model.dto.AsignaturaDTO;
import com.universityproject.model.exception.*;
import com.universityproject.repository.AlumnoRepository;
import com.universityproject.repository.MateriaRepository;
import com.universityproject.service.AlumnoService;
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
     * @throws MateriaNotFoundException Si la materia con el ID especificado no se encuentra.
     */
    @Override
    public AlumnoDTO crearAlumno(AlumnoDTO alumnoDTO) {
        Alumno alumno = new Alumno();
        alumno.setNombre(alumnoDTO.getNombre());
        alumno.setApellido(alumnoDTO.getApellido());
        alumno.setDni(alumnoDTO.getDni());

        List<Asignatura> asignaturas = alumnoDTO.getAsignaturas().stream()
                .map(asignaturaDTO -> {
                    Asignatura asignatura = new Asignatura();
                    String materiaId = materiaRepository.findByNombre(asignaturaDTO.getMateriaNombre())
                            .orElseThrow(() -> new MateriaNotFoundException("Materia no encontrada"))
                            .getId();

                    asignatura.setMateriaId(materiaId);
                    asignatura.setEstadoAsignatura(EstadoAsignatura.NO_CURSADA);
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
     * @throws AlumnoNotFoundException Si el alumno con el ID especificado no se encuentra.
     */
    @Override
    public AlumnoDTO modificarAlumno(String id, AlumnoDTO alumnoDTO) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new AlumnoNotFoundException("Alumno no encontrado"));

        alumno.setNombre(alumnoDTO.getNombre());
        alumno.setApellido(alumnoDTO.getApellido());
        alumno.setDni(alumnoDTO.getDni());

        alumnoRepository.save(alumno);
        return mapToDTO(alumno);
    }

    /**
     * Elimina un alumno del sistema.
     * @param id El ID del alumno a eliminar.
     * @throws AlumnoNotFoundException Si el alumno con el ID especificado no se encuentra.
     */
    @Override
    public void eliminarAlumno(String id) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new AlumnoNotFoundException("Alumno no encontrado"));
        alumnoRepository.delete(alumno);
    }

    /**
     * Obtiene un alumno por su ID.
     * @param id El ID del alumno.
     * @return El DTO del alumno encontrado.
     * @throws AlumnoNotFoundException Si el alumno con el ID especificado no se encuentra.
     */
    @Override
    public AlumnoDTO obtenerAlumnoPorId(String id) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new AlumnoNotFoundException("Alumno no encontrado"));
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
     * @return El DTO del alumno con el estado actualizado.
     * @throws AlumnoNotFoundException Si no se encuentra el alumno con el ID especificado.
     * @throws AsignaturaNotFoundException Si la asignatura con el ID especificado no se encuentra.
     */
    @Override
    public AlumnoDTO actualizarEstadoAsignatura(String idAlumno, String idAsignatura, EstadoAsignatura estado) {
        Alumno alumno = alumnoRepository.findById(idAlumno)
                .orElseThrow(() -> new AlumnoNotFoundException("Alumno no encontrado"));

        Asignatura asignatura = alumno.getAsignaturas().stream()
                .filter(a -> a.getMateriaId().equals(idAsignatura))
                .findFirst()
                .orElseThrow(() -> new AsignaturaNotFoundException("Asignatura no encontrada"));

        asignatura.setEstadoAsignatura(estado);
        alumnoRepository.save(alumno);
        return mapToDTO(alumno);
    }

    private AlumnoDTO mapToDTO(Alumno alumno) {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId(alumno.getId());
        alumnoDTO.setNombre(alumno.getNombre());
        alumnoDTO.setApellido(alumno.getApellido());
        alumnoDTO.setDni(alumno.getDni());

        List<AsignaturaDTO> asignaturasDTO = alumno.getAsignaturas().stream().map(asignatura -> {
            AsignaturaDTO asignaturaDTO = new AsignaturaDTO();
            asignaturaDTO.setMateriaNombre(materiaRepository.findById(asignatura.getMateriaId())
                    .orElseThrow(() -> new AsignaturaNotFoundException("Materia no encontrada"))
                    .getNombre());
            asignaturaDTO.setEstadoAsignatura(asignatura.getEstadoAsignatura());
            return asignaturaDTO;
        }).collect(Collectors.toList());

        alumnoDTO.setAsignaturas(asignaturasDTO);
        return alumnoDTO;
    }
}


