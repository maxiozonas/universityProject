package com.universityproject.model.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class AlumnoDTO {

    private String id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 2, message = "El apellido debe tener al menos 2 caracteres")
    private String apellido;

    @NotBlank(message = "El dni no puede estar vacío")
    @Size(min = 7, max = 8, message = "El dni debe tener entre 7 y 8 caracteres")
    @Pattern(regexp = "\\d{7,8}", message = "El dni debe contener solo dígitos y tener entre 7 y 8 caracteres")
    private String dni;

    private List<AsignaturaDTO> asignaturas;
}

