package com.testdata.controller;

import com.testdata.model.Patient;
import com.testdata.service.PatientService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/patients/admitted")
    public Patient createAdmittedPatient() {
        return patientService.createAdmittedPatient();
    }

    @PostMapping("/patients/icu")
    public Patient createICUPatient() {
        return patientService.createICUPatient();
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
    @PostMapping("/patients/bulk")
    public List<Patient> createBulkPatients(@RequestParam int count) {
        return patientService.createBulkPatients(count);
    }
}
