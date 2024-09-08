package com.universityproject.repository;

import com.universityproject.model.Materia;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MateriaRepository extends MongoRepository<Materia, String> {
    List<Materia> findByNombreContainingIgnoreCase(String nombre);
    Optional<Materia> findByNombre(String nombre);
}