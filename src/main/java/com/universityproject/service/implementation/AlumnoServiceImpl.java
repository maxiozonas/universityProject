package com.universityproject.service.implementation;

import com.universityproject.model.dto.AlumnoDto;
import com.universityproject.model.Alumno;
import com.universityproject.model.Asignatura;
import com.universityproject.model.EstadoAsignatura;
import com.universityproject.repository.AlumnoRepository;
import com.universityproject.service.AlumnoService;
import com.universityproject.service.exception.AlumnoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Override
    public AlumnoDto crearAlumno(AlumnoDto alumnoDto) {
        try {
            Alumno alumno = mapearDTOAEntidad(alumnoDto);
            Alumno alumnoGuardado = alumnoRepository.save(alumno);
            return mapearEntidadADTO(alumnoGuardado);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el alumno: " + e.getMessage());
        }
    }

    @Override
    public List<AlumnoDto> obtenerTodosLosAlumnos() {
        try {
            List<Alumno> alumnos = alumnoRepository.findAll();
            if (alumnos.isEmpty()) {
                throw new Exception("No hay alumnos registrados.");
            }
            return alumnos.stream()
                    .map(this::mapearEntidadADTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de alumnos: " + e.getMessage());
        }
    }

    @Override
    public AlumnoDto obtenerAlumnoPorId(String id) {
        try {
            Alumno alumno = alumnoRepository.findById(id)
                    .orElseThrow(() -> new AlumnoNotFoundException("Alumno no encontrado con el ID: " + id));
            return mapearEntidadADTO(alumno);
        } catch (AlumnoNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el alumno: " + e.getMessage());
        }
    }

    @Override
    public AlumnoDto actualizarAlumno(String id, AlumnoDto alumnoDto) {
        try {
            Alumno alumno = mapearDTOAEntidad(alumnoDto);
            alumno.setId(id);
            Alumno alumnoActualizado = alumnoRepository.save(alumno);
            return mapearEntidadADTO(alumnoActualizado);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el alumno: " + e.getMessage());
        }
    }

    @Override
    public void eliminarAlumno(String id) {
        try {
            alumnoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el alumno: " + e.getMessage());
        }
    }

    @Override
    public AlumnoDto actualizarAsignaturaDeAlumno(String idAlumno, String idAsignatura, EstadoAsignatura nuevoEstado) {
        try {
            Alumno alumno = alumnoRepository.findById(idAlumno)
                    .orElseThrow(() -> new AlumnoNotFoundException("Alumno no encontrado con el ID: " + idAlumno));

            Asignatura asignatura = alumno.getAsignaturas().stream()
                    .filter(a -> a.getId().equals(idAsignatura))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con el ID: " + idAsignatura));

            asignatura.setEstado(nuevoEstado);
            Alumno alumnoActualizado = alumnoRepository.save(alumno);
            return mapearEntidadADTO(alumnoActualizado);
        } catch (AlumnoNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la asignatura del alumno: " + e.getMessage());
        }
    }

    private Alumno mapearDTOAEntidad(AlumnoDto alumnoDto) {
        Alumno alumno = new Alumno();
        alumno.setNombre(alumnoDto.getNombre());
        alumno.setApellido(alumnoDto.getApellido());
        alumno.setDni(alumnoDto.getDni());
        alumno.setAsignaturas(alumnoDto.getAsignaturas());
        return alumno;
    }

    private AlumnoDto mapearEntidadADTO(Alumno alumno) {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setId(alumno.getId());
        alumnoDto.setNombre(alumno.getNombre());
        alumnoDto.setApellido(alumno.getApellido());
        alumnoDto.setDni(alumno.getDni());
        alumnoDto.setAsignaturas(alumno.getAsignaturas());
        return alumnoDto;
    }
}
