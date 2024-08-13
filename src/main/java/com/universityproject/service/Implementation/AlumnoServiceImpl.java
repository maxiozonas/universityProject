package com.universityproject.service.Implementation;

import com.universityproject.dto.AlumnoDto;
import com.universityproject.model.Alumno;
import com.universityproject.model.Asignatura;
import com.universityproject.model.EstadoAsignatura;
import com.universityproject.repository.AlumnoRepository;
import com.universityproject.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Override
    public AlumnoDto crearAlumno(AlumnoDto AlumnoDto) {
        Alumno alumno = mapearDTOAEntidad(AlumnoDto);
        Alumno alumnoGuardado = alumnoRepository.save(alumno);
        return mapearEntidadADTO(alumnoGuardado);
    }

    @Override
    public List<AlumnoDto> obtenerTodosLosAlumnos() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        return alumnos.stream()
                .map(this::mapearEntidadADTO)
                .collect(Collectors.toList());
    }

    @Override
    public AlumnoDto obtenerAlumnoPorId(String id) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        return mapearEntidadADTO(alumno);
    }

    @Override
    public AlumnoDto actualizarAlumno(String id, AlumnoDto alumnoDto) {
        Alumno alumno = mapearDTOAEntidad(alumnoDto);
        alumno.setId(id);
        Alumno alumnoActualizado = alumnoRepository.save(alumno);
        return mapearEntidadADTO(alumnoActualizado);
    }

    @Override
    public void eliminarAlumno(String id) {
        alumnoRepository.deleteById(id);
    }

    private Alumno mapearDTOAEntidad(AlumnoDto AlumnoDto) {
        Alumno alumno = new Alumno();
        alumno.setNombre(AlumnoDto.getNombre());
        alumno.setApellido(AlumnoDto.getApellido());
        alumno.setDni(AlumnoDto.getDni());
        alumno.setAsignaturas(AlumnoDto.getAsignaturas());
        return alumno;
    }

    private AlumnoDto mapearEntidadADTO(Alumno alumno) {
        AlumnoDto AlumnoDto = new AlumnoDto();
        AlumnoDto.setId(alumno.getId());
        AlumnoDto.setNombre(alumno.getNombre());
        AlumnoDto.setApellido(alumno.getApellido());
        AlumnoDto.setDni(alumno.getDni());
        AlumnoDto.setAsignaturas(alumno.getAsignaturas());
        return AlumnoDto;
    }

    @Override
    public AlumnoDto actualizarAsignaturaDeAlumno(String idAlumno, String idAsignatura, EstadoAsignatura nuevoEstado) {
        Alumno alumno = alumnoRepository.findById(idAlumno)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        Asignatura asignatura = alumno.getAsignaturas().stream()
                .filter(a -> a.getId().equals(idAsignatura))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));

        asignatura.setEstado(nuevoEstado);
        Alumno alumnoActualizado = alumnoRepository.save(alumno);
        return mapearEntidadADTO(alumnoActualizado);
    }
}
