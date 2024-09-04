package com.universityproject.repository;

import com.universityproject.model.Carrera;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarreraRepository extends MongoRepository<Carrera, String> {
}
