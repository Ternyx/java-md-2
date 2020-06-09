package com.github.ternyx.controllers;

import javax.websocket.server.PathParam;
import com.github.ternyx.services.impl.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * DoctorsController
 */
@Controller
@RequestMapping("/doctors")
public class DoctorsController {

    @Autowired
    HospitalService hospitalService;

    @GetMapping("/showAllPatients")
    public String showAllPatients(Model model) {
        model.addAttribute("patients", hospitalService.selectAllPatients());
        return "showPatients";
    }

    @GetMapping("/selectPatientsByNameAndSurname")
    public String selectPatientsByNameAndSurname(Model model, @PathParam(value = "name") String name, @PathParam(value = "surname") String surname) {
        model.addAttribute("patients", hospitalService.selectPatientByNameAndSurname(name, surname));
        return "showPatients";
    }
}
