package com.github.ternyx.controllers;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import com.github.ternyx.models.Doctor;
import com.github.ternyx.models.HospitalUserPrincipal;
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
 * DoctorController
 */
@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    HospitalService hospitalService;

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "insertDoctor";
    };

    @PostMapping("/register")
    public String postRegister(Model model, @Valid Doctor doctor, BindingResult result) {
        if (result.hasErrors()) {
            return "insertDoctor";
        }
        try {
            hospitalService.insertDoctor(doctor);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "insertDoctor";
        }
    }

    @GetMapping("/showAllAppointmentsByDoctor")
    public String showAllAppointmentsByDoctor(Model model, Authentication auth) {
        Doctor doc = (Doctor)((HospitalUserPrincipal)auth.getPrincipal()).getUser();

        model.addAttribute("appointments", hospitalService.selectAllAppointmentsByDoctorId(doc.getUserId()));
        return "showAppointments";
    }

    @GetMapping("/showAllAppointmentsByDoctorToday")
    public String showAllAppointmentsByDoctorToday(Model model, Authentication auth) {
        Doctor doc = (Doctor)((HospitalUserPrincipal)auth.getPrincipal()).getUser();

        model.addAttribute("appointments", hospitalService.selectAllAppointmentsByDoctorToday(doc.getUserId()));
        return "showAppointments";
    }

    @GetMapping("/selectPatientByNameAndSurname")
    public String selectPatientsByNameAndSurname(Model model, @RequestParam(name = "name") String name, @RequestParam(name = "surname") String surname) {

        model.addAttribute("patients", hospitalService.selectPatientByNameAndSurname(name, surname));
        return "showPatients";
    }


}
