package com.testdata.service;

import com.testdata.model.Patient;
import com.testdata.repository.PatientRepository;

import java.util.ArrayList;
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

    @Transactional
    public Patient createAdmittedPatient() {
        Patient patient = createRegisteredPatient();
        patientScenarioGenerator.admittedPatient(patient);
        return patientRepository.save(patient);
    }

    @Transactional
    public Patient createICUPatient() {
        Patient patient = createRegisteredPatient();
        patientScenarioGenerator.icuPatient(patient);
        return patientRepository.save(patient);
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }
    public List<Patient> createBulkPatients(int count) {

        List<Patient> patients = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            patients.add(createRegisteredPatient());
        }

        return patients;
    }
    @Transactional
    public void cleanupPatients() {
        patientRepository.deleteAllInBatch();
        ssnAllocationService.markAllUnused();
    }
}
