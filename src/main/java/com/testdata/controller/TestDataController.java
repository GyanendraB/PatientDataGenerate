package com.testdata.controller;

import com.testdata.dto.BulkScenarioRequest;
import com.testdata.dto.FullAdmissionResponse;
import com.testdata.dto.StatsResponse;
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
    public Patient createRegisteredPatient(@RequestParam(required = false) String testRunId) {
        return patientService.createRegisteredPatient(testRunId);
    }

    @PostMapping("/patients/admitted")
    public Patient createAdmittedPatient(@RequestParam(required = false) String testRunId) {
        return patientService.createPatientByStatus("admitted", testRunId);
    }

    @PostMapping("/patients/icu")
    public Patient createIcuPatient(@RequestParam(required = false) String testRunId) {
        return patientService.createPatientByStatus("icu", testRunId);
    }

    @PostMapping("/patients/discharged")
    public Patient createDischargedPatient(@RequestParam(required = false) String testRunId) {
        return patientService.createPatientByStatus("discharged", testRunId);
    }

    @PostMapping("/scenarios/full-admission")
    public FullAdmissionResponse createFullAdmissionScenario(@RequestParam(required = false) String testRunId) {
        return patientService.createFullAdmissionScenario(testRunId);
    }

    @PostMapping("/scenarios/bulk")
    public List<Patient> createBulkScenarioPatients(@RequestBody BulkScenarioRequest request) {
        return patientService.createBulkScenarios(request);
    }

    @GetMapping("/stats")
    public StatsResponse getStats() {
        return patientService.getStats();
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

    @DeleteMapping("/reset/{testRunId}")
    public ResponseEntity<Void> cleanupByTestRun(@PathVariable String testRunId) {
        patientService.cleanupByTestRun(testRunId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/patients/bulk")
    public List<Patient> createBulkPatients(@RequestParam int count,
                                            @RequestParam(required = false) String testRunId) {
        return patientService.createBulkPatients(count, testRunId);
    }
}
