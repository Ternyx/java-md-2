package com.github.ternyx.repos;

import java.util.Collection;
import java.util.Date;
import com.github.ternyx.models.Appointment;
import com.github.ternyx.models.Doctor;
import com.github.ternyx.models.Patient;
import com.github.ternyx.models.enums.DoctorType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * AppointmentRepo
 */
@Repository
public interface AppointmentRepo extends CrudRepository<Appointment, Integer> {
    boolean existsByDoctorAndPatientAndDate(Doctor doctor, Patient patient, Date date);

    @Query("SELECT a FROM Appointment a WHERE a.doctor IN (SELECT d FROM Doctor d WHERE d.type = ?1)")
    Collection<Appointment> findByDoctorType(DoctorType type);

    Collection<Appointment> findAll();
}
