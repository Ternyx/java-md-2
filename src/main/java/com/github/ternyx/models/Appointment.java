
package com.github.ternyx.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.FutureOrPresent;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Appointment
 */
@Entity(name = "Appointment")
@Getter @Setter @NoArgsConstructor 
public class Appointment {

    @Column(name = "appointment_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(value = AccessLevel.PRIVATE)
    private int appointmentId;

    @Column(name = "date")
    @FutureOrPresent
    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    @NonNull
    @OneToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "user_id")
    private Doctor doctor;

    @OneToOne
    @NonNull
    @JoinColumn(name = "patient_id", referencedColumnName = "user_id")
    private Patient patient;

    public Appointment(@FutureOrPresent @NonNull Date date, @NonNull Doctor doctor,
            @NonNull Patient patient) {
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Appointment [appointmentId=" + appointmentId + ", date=" + date + "]";
    }

}
