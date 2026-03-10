package com.testdata.controller;

import com.testdata.generator.PatientScenarioGenerator;
import com.testdata.model.Patient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestDataController {

    @GetMapping("/testdata/patient/registered")
    public Patient registeredPatient() {

        return PatientScenarioGenerator.registeredPatient();
    }

    @GetMapping("/testdata/patient/admitted")
    public Patient admittedPatient() {

        return PatientScenarioGenerator.admittedPatient();
    }

    @GetMapping("/testdata/patient/icu")
    public Patient icuPatient() {

        return PatientScenarioGenerator.icuPatient();
    }
}
