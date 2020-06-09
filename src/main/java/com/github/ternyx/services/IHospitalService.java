package com.github.ternyx.services;

import java.util.Collection;
import com.github.ternyx.models.Appointment;
import com.github.ternyx.models.Doctor;
import com.github.ternyx.models.Patient;
import com.github.ternyx.models.User;
import com.github.ternyx.models.enums.DoctorType;

/**
 * IHospitalService
 */
public interface IHospitalService {
    void inputData();

    Collection<Doctor> selectAllDoctors();

    Collection<Doctor> selectAllDoctorsByType(DoctorType type);

    Collection<Patient> selectAllPatients();

    Collection<Patient> selectPatientByNameAndSurname(String name, String surname);

    boolean insertPatient(Patient patient);

    boolean insertDoctor(Doctor doctor);

    boolean updatePatientById(int patientId, Patient patient);

    boolean deletePatientById(int patientId);

    boolean authenticateUser(User user);

    boolean insertNewAppointment(Appointment appointment);

    Collection<Appointment> selectAllAppointmentsByPatientId(int patientId);

    Collection<Appointment> selectAllAppointmentsByDoctorId(int doctorId);

    Collection<Appointment> selectAllAppointmentsByDoctorToday(int doctorId);
}
