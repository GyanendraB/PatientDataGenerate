package com.testdata.controller;

import com.testdata.generator.PatientGenerator;
import com.testdata.model.Patient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestDataController {

    @GetMapping("/testdata/patient")
    public Patient generatePatient() {
        return PatientGenerator.generatePatient();
    }
}
