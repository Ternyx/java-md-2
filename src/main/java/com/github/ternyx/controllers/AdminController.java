package com.github.ternyx.controllers;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import com.github.ternyx.models.Appointment;
import com.github.ternyx.models.Patient;
import com.github.ternyx.models.enums.DoctorType;
import com.github.ternyx.services.impl.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * AdminController
 */
@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    HospitalService hospitalService;

    @GetMapping("/inputdata")
    public void inputData() {
        hospitalService.inputData();
    }

    @GetMapping("/updatePatientById")
    public String getUpdatePatientById(Model model, @RequestParam(name = "id") int patientId) {
        Optional<Patient> targetPatient = hospitalService.selectPatientById(patientId);

        if (targetPatient.isEmpty()) {
            model.addAttribute("errorMessage", "Patient with " + patientId + " does not exist");
            return "errorMessage";
        }

        model.addAttribute("patient", targetPatient.get());
        return "updatePatient";
    }

    @PostMapping("/updatePatientById")
    public String postUpdatePatientById(Model model, Patient patient,
            @RequestParam(name = "id") int patientId, BindingResult result) {
        if (result.hasErrors()) {
            return "updatePatient";
        }

        try {
            hospitalService.updatePatientById(patientId, patient);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errorMessage";
        }
    }

    @GetMapping("/deletePatientById")
    public String deletePatientById(Model model, @RequestParam(name = "id") int patientId) {
        try {
            hospitalService.deletePatientById(patientId);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errorMessage";
        }
        return "redirect:/";
    }

    @GetMapping("/showAppointmentsByType")
    public String getShowAppointmentsByType() {
        return "selectDoctorType";
    }

    @PostMapping("/showAppointmentsByType")
    public String postShowAppointmentsByType(Model model,
            @RequestParam(name = "doctorType") DoctorType type) {
        Collection<Appointment> appointments = hospitalService.selectAppointmentsByType(type);
        model.addAttribute("appointments", appointments);
        return "showAppointments";
    }

    @GetMapping("/appointmentsInDate")
    public String getAppointmentsInSpecificDate(Model model,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(name = "date") Date date) {
        Collection<Appointment> appointments = hospitalService.selectAllApointmentsInDate(date);
        model.addAttribute("appointments", appointments);
        model.addAttribute("count", appointments.size());

        return "showAppointments";
    }
}



