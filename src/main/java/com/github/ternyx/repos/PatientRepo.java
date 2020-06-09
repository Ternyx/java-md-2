package com.github.ternyx.repos;

import java.util.Collection;
import com.github.ternyx.models.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * PatientRepo
 */
@Repository
public interface PatientRepo extends CrudRepository<Patient, Integer> {
    Collection<Patient> findByNameAndSurname(String name, String surname);

    Collection<Patient> findAll();

    boolean existsByNameAndSurname(String name, String surname);
}
