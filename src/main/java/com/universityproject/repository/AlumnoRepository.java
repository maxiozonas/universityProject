package com.universityproject.repository;

import com.universityproject.model.Alumno;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends MongoRepository<Alumno, String> {
}