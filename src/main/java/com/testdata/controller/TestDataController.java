package com.testdata.controller;

import com.testdata.model.Patient;
import com.testdata.service.PatientService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testdata")
public class TestDataController {

    private final PatientService patientService;

    public TestDataController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/patients/registered")
    public Patient createRegisteredPatient() {
        return patientService.createRegisteredPatient();
    }

    @GetMapping("/patients")
    public List<Patient> getPatients() {
        return patientService.findAll();
    }

    @DeleteMapping("/cleanup/patients")
    public ResponseEntity<Void> cleanupPatients() {
        patientService.cleanupPatients();
        return ResponseEntity.noContent().build();
    }
}
