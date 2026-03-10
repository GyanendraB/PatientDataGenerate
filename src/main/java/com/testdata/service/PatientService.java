package com.testdata.service;

import com.testdata.model.Patient;
import com.testdata.repository.PatientRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientScenarioGenerator patientScenarioGenerator;
    private final SsnAllocationService ssnAllocationService;

    public PatientService(PatientRepository patientRepository,
                          PatientScenarioGenerator patientScenarioGenerator,
                          SsnAllocationService ssnAllocationService) {
        this.patientRepository = patientRepository;
        this.patientScenarioGenerator = patientScenarioGenerator;
        this.ssnAllocationService = ssnAllocationService;
    }

    @Transactional
    public Patient createRegisteredPatient() {
        String ssn = ssnAllocationService.getAvailableSSN();
        Patient patient = patientScenarioGenerator.registeredPatient(ssn);
        return patientRepository.save(patient);
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Transactional
    public void cleanupPatients() {
        patientRepository.deleteAllInBatch();
        ssnAllocationService.markAllUnused();
    }
}
