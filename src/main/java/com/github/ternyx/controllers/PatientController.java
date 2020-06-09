package com.github.ternyx.controllers;

import java.util.Optional;
import javax.validation.Valid;
import com.github.ternyx.models.Appointment;
import com.github.ternyx.models.HospitalUserPrincipal;
import com.github.ternyx.models.Patient;
import com.github.ternyx.models.enums.DoctorType;
import com.github.ternyx.services.impl.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * PatientController
 */
@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    HospitalService hospitalService;

    @GetMapping("/showAllDoctors")
    public String showAllDoctors(Model model) {
        model.addAttribute("doctors", hospitalService.selectAllDoctors());
        return "showAllDoctors";
    }

    @GetMapping("/showAllDoctorsByType")
    public String showAllDoctorsByType(Model model) {
        model.addAttribute("doctorTypes", DoctorType.values());
        return "showAllDoctorsByType";
    }

    @PostMapping("/showAllDoctorsByType")
    public String postShowDoctorsByType(Model model, @RequestParam(name = "doctorType") DoctorType doctorType) {
        hospitalService.selectAllDoctorsByType(doctorType);
        model.addAttribute("doctors", hospitalService.selectAllDoctorsByType(doctorType));
        return "showAllDoctors";
    }

    @GetMapping("/register")
    public String registerPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "insertPatient";
    }

    @PostMapping("/register")
    public String postRegisterPatient(Model model, @Valid Patient patient, BindingResult result) {
        if (!result.hasErrors()) {
            try {
                hospitalService.insertPatient(patient);
                return "redirect:/login";
            } catch (Exception e) {
                model.addAttribute("errorMessage", e.getMessage());
                return "insertPatient";
            }
        } else {
            return "insertPatient";
        }
    }

    @GetMapping("/insertNewAppointment")
    public String getInsertNewAppointment(Model model, Authentication auth) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctorList", hospitalService.selectAllDoctors());
        return "insertAppointment";
    }

    @PostMapping("/insertNewAppointment")
    public String postInsertNewAppointment(@Valid Appointment appointment, BindingResult result, Model model, Authentication auth) {
        Patient pat = (Patient)((HospitalUserPrincipal)auth.getPrincipal()).getUser();
        appointment.setPatient(pat);

        if (result.hasErrors()) {
            model.addAttribute("appointment", appointment);
            model.addAttribute("doctorList", hospitalService.selectAllDoctors());
            return "insertAppointment";
        } 
        try {
            hospitalService.insertNewAppointment(appointment);
            return "redirect:/patient/showAllAppointmentsByPatient";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "insertAppointment";
        }
    }

    @GetMapping("/showAllAppointmentsByPatient")
    public String showAllAppointmentsByPatient(Model model, Authentication auth) {
        Patient pat = (Patient)((HospitalUserPrincipal)auth.getPrincipal()).getUser();
        model.addAttribute("appointments", hospitalService.selectAllAppointmentsByPatientId(pat.getUserId()));
        model.addAttribute("targetPatient", pat);

        return "showAppointments";
    }

    @GetMapping("/postponeAppointment")
    public String getPostponeAppointment(Model model, Authentication auth, @RequestParam(name = "id") int id) {
        //Patient pat = (Patient)((HospitalUserPrincipal)auth.getPrincipal()).getUser();
        Optional<Appointment> appointment = hospitalService.selectAppointmentsById(id);

        model.addAttribute("appointment", appointment.get());
        return "postponeAppointment";
    }

    @PostMapping("/postponeAppointment")
    public String postPostponeAppointment(Model model, @Valid Appointment appointment, BindingResult result, @RequestParam(name = "id") int id) {

        if (result.hasErrors()) {
            return "postponeAppointment";
        }

        try {
            hospitalService.updateAppointment(id, appointment);
            return "redirect:/patient/showAllAppointmentsByPatient";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "postponeAppointment";
        }
    }
}
