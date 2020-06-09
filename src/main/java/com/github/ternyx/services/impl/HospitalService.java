package com.github.ternyx.services.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.github.ternyx.models.Appointment;
import com.github.ternyx.models.Doctor;
import com.github.ternyx.models.Patient;
import com.github.ternyx.models.User;
import com.github.ternyx.models.enums.DoctorType;
import com.github.ternyx.repos.AppointmentRepo;
import com.github.ternyx.repos.DoctorRepo;
import com.github.ternyx.repos.PatientRepo;
import com.github.ternyx.repos.UserRepo;
import com.github.ternyx.services.IHospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * HospitalService
 */
@Service
public class HospitalService implements IHospitalService {

    @Autowired
    PatientRepo patientRepo;

    @Autowired
    DoctorRepo doctorRepo;

    @Autowired
    AppointmentRepo appointmentRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public void inputData() {
        String encodedPassword = passwordEncoder.encode("password");

        Doctor d1 = new Doctor("doctor1", encodedPassword, "Michael", "Jackson", DoctorType.DENTIST);
        Doctor d2 = new Doctor("doctor2", encodedPassword, "Michelle", "Obama", DoctorType.SURGEON);

        Patient p1 = new Patient("patient1", encodedPassword, "Omar", "Salah");
        Patient p2 = new Patient("patient2", encodedPassword, "Lewis", "Hamilton");

        User admin = new User("admin", encodedPassword, "Admin", "Admin", "ROLE_ADMIN");

        Appointment a1 = new Appointment(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)), d1, p1);
        Appointment a2 = new Appointment(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)), d1, p2);

        Appointment a3 = new Appointment(Date.from(Instant.now().plus(2, ChronoUnit.DAYS)), d2, p1);
        Appointment a4 = new Appointment(Date.from(Instant.now().plus(2, ChronoUnit.DAYS)), d2, p2);

        userRepo.save(admin);

        doctorRepo.saveAll(List.of(
            d1, d2
        ));

        patientRepo.saveAll(List.of(
            p1, p2
        ));

        appointmentRepo.saveAll(List.of(
            a1, a2, a3, a4
        ));
    }

    @Override
    public Collection<Doctor> selectAllDoctors() {
        return doctorRepo.findAll();
    }

    @Override
    public Collection<Doctor> selectAllDoctorsByType(DoctorType type) {
        return doctorRepo.findByType(type);
    }

    @Override
    public Collection<Patient> selectAllPatients() {
        return patientRepo.findAll();
    }

    @Override
    public Collection<Patient> selectPatientByNameAndSurname(String name, String surname) {
        return patientRepo.findByNameAndSurname(name, surname);
    }

    @Override
    public boolean insertPatient(Patient patient) {
        if (userRepo.findByUsername(patient.getUsername()).isEmpty()) {
            patient.setPassword(passwordEncoder.encode(patient.getPassword()));
            patientRepo.save(patient);
            return true;
        }
        throw new IllegalArgumentException("User with that username already exists");
    }

    @Override
    public boolean insertDoctor(Doctor doctor) {
        if (userRepo.findByUsername(doctor.getUsername()).isEmpty()) {
            doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
            doctorRepo.save(doctor);
            return true;
        }
        throw new IllegalArgumentException("User with that username already exists");
    }

    @Override
    public boolean updatePatientById(int patientId, Patient patient) {
        Optional<Patient> targetPatient = patientRepo.findById(patientId);
        if (!targetPatient.isEmpty()) {
            Patient target = targetPatient.get();

            target.setName(patient.getName());
            target.setSurname(patient.getSurname());
            patientRepo.save(target);
            return true;
        }
        throw new IllegalArgumentException("Patient with id " + patientId + " does not exist");
    }

    @Override
    public boolean deletePatientById(int patientId) {
        if (!patientRepo.existsById(patientId)) {
            throw new IllegalArgumentException("Patient with id " + patientId + " does not exist");
        }
        patientRepo.deleteById(patientId);
        return true;
    }

    @Override
    public boolean authenticateUser(User user) {
        return false;
    }

    @Override
    public boolean insertNewAppointment(Appointment appointment) {
        if (!appointmentRepo.existsByDoctorAndPatientAndDate(appointment.getDoctor(),
                appointment.getPatient(), appointment.getDate())) {

            appointmentRepo.save(appointment);
            return true;
        }

        throw new IllegalArgumentException("Appointment already exists");
    }

    @Override
    public Collection<Appointment> selectAllAppointmentsByPatientId(int patientId) {
        Optional<Patient> targetPatient = patientRepo.findById(patientId);

        if (targetPatient.isEmpty()) {
            return new ArrayList<Appointment>();
        }
        return targetPatient.get().getAppointments();
    }


    @Override
    public Collection<Appointment> selectAllAppointmentsByDoctorId(int doctorId) {
        Optional<Doctor> targetDoctor = doctorRepo.findById(doctorId);

        if (targetDoctor.isEmpty()) {
            return new ArrayList<Appointment>();
        }
        return targetDoctor.get().getAppointments();
    }

    @Override
    public Collection<Appointment> selectAllAppointmentsByDoctorToday(int doctorId) {
        Optional<Doctor> targetDoctor = doctorRepo.findById(doctorId);

        if (targetDoctor.isEmpty()) {
            return new ArrayList<Appointment>();
        }

        Instant startOfInstant = Instant.now().truncatedTo(ChronoUnit.DAYS);
        Date endOfDay = Date.from(startOfInstant.plus(1, ChronoUnit.DAYS));
        Date startOfDay = Date.from(startOfInstant);

        return targetDoctor.get().getAppointments().stream()
            .filter(d -> d.getDate().compareTo(endOfDay) <= 0 && d.getDate().compareTo(startOfDay) >= 0)
            .sorted((a, b) -> a.getDate().compareTo(b.getDate()))
            .collect(Collectors.toList());
    }

    public Optional<Patient> selectPatientById(int patientId) {
        return patientRepo.findById(patientId);
    }

    public Collection<Appointment> selectAppointmentsByType(DoctorType type) {
        return appointmentRepo.findByDoctorType(type);
    }

    public Optional<Appointment> selectAppointmentsById(int id) {
        return appointmentRepo.findById(id);
    }

    public void updateAppointment(int appointmentId, Appointment appointment) {
        Optional<Appointment> optAppointment = appointmentRepo.findById(appointmentId);

        if (optAppointment.isEmpty()) {
            throw new IllegalArgumentException(
                    "Appointment with id " + appointmentId + " does not exist");
        }

        Appointment targetAppointment = optAppointment.get();

        targetAppointment.setDate(appointment.getDate());
        targetAppointment.setDoctor(appointment.getDoctor());
        targetAppointment.setPatient(appointment.getPatient());

        appointmentRepo.save(targetAppointment);
    }

    public Collection<Appointment> selectAllApointmentsInDate(Date date) {
        Instant startOfInstant = Instant.ofEpochMilli(date.getTime()).truncatedTo(ChronoUnit.DAYS);
        Date endOfDay = Date.from(startOfInstant.plus(1, ChronoUnit.DAYS));
        Date startOfDay = Date.from(startOfInstant);

        return appointmentRepo.findAll().stream()
            .filter(a -> a.getDate().compareTo(startOfDay) >= 0 && a.getDate().compareTo(endOfDay) <= 0)
            .collect(Collectors.toList());
    }
    
}
