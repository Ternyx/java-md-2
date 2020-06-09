package com.github.ternyx.repos;

import java.util.Collection;
import com.github.ternyx.models.Doctor;
import com.github.ternyx.models.enums.DoctorType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * DoctorRepo
 */
@Repository
public interface DoctorRepo extends CrudRepository<Doctor, Integer>{
    Collection<Doctor> findByType(DoctorType type);

    Collection<Doctor> findAll();

    boolean existsByNameAndSurname(String name, String surname);
}
