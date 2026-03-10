package com.testdata.service;

import com.github.javafaker.Faker;
import com.testdata.dto.BulkScenarioRequest;
import com.testdata.dto.FullAdmissionResponse;
import com.testdata.dto.StatsResponse;
import com.testdata.model.Patient;
import com.testdata.repository.PatientRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientScenarioGenerator patientScenarioGenerator;
    private final SsnAllocationService ssnAllocationService;
    private final Faker faker = new Faker();

    public PatientService(PatientRepository patientRepository,
                          PatientScenarioGenerator patientScenarioGenerator,
                          SsnAllocationService ssnAllocationService) {
        this.patientRepository = patientRepository;
        this.patientScenarioGenerator = patientScenarioGenerator;
        this.ssnAllocationService = ssnAllocationService;
    }

    @Transactional
    public Patient createRegisteredPatient(String testRunId) {
        return createPatientByStatus("registered", testRunId);
    }

    @Transactional
    public Patient createPatientByStatus(String state, String testRunId) {
        String ssn = ssnAllocationService.getAvailableSSN();

        Patient patient = switch (state.toLowerCase()) {
            case "registered" -> patientScenarioGenerator.registeredPatient(ssn, testRunId);
            case "admitted" -> patientScenarioGenerator.admittedPatient(ssn, testRunId);
            case "icu" -> patientScenarioGenerator.icuPatient(ssn, testRunId);
            case "discharged" -> patientScenarioGenerator.dischargedPatient(ssn, testRunId);
            default -> throw new IllegalArgumentException("Unsupported patient state: " + state);
        };

        return patientRepository.save(patient);
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public List<Patient> createBulkPatients(int count, String testRunId) {
        List<Patient> patients = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            patients.add(createRegisteredPatient(testRunId));
        }

        return patients;
    }

    public List<Patient> createBulkScenarios(BulkScenarioRequest request) {
        List<Patient> generated = IntStream.range(0, request.registeredPatients())
                .parallel()
                .mapToObj(i -> createPatientByStatus("registered", request.testRunId()))
                .toList();

        List<Patient> admitted = IntStream.range(0, request.admittedPatients())
                .parallel()
                .mapToObj(i -> createPatientByStatus("admitted", request.testRunId()))
                .toList();

        List<Patient> icu = IntStream.range(0, request.icuPatients())
                .parallel()
                .mapToObj(i -> createPatientByStatus("icu", request.testRunId()))
                .toList();

        List<Patient> all = new ArrayList<>();
        all.addAll(generated);
        all.addAll(admitted);
        all.addAll(icu);
        return all;
    }

    @Transactional
    public FullAdmissionResponse createFullAdmissionScenario(String testRunId) {
        Patient patient = createPatientByStatus("icu", testRunId);

        return new FullAdmissionResponse(
                patient.getPatientId(),
                "DOC-" + faker.number().numberBetween(10, 99),
                "APT-" + UUID.randomUUID().toString().substring(0, 6),
                "ADM-" + UUID.randomUUID().toString().substring(0, 8),
                patient.getBedNumber(),
                "BILL-" + UUID.randomUUID().toString().substring(0, 8),
                patient.getStatus()
        );
    }

    public StatsResponse getStats() {
        long totalPatients = patientRepository.count();
        long totalSsn = ssnAllocationService.total();
        long usedSsn = ssnAllocationService.used();
        return new StatsResponse(totalPatients, totalSsn, usedSsn, totalSsn - usedSsn);
    }

    @Transactional
    public void cleanupPatients() {
        patientRepository.deleteAllInBatch();
        ssnAllocationService.markAllUnused();
    }

    @Transactional
    public void cleanupByTestRun(String testRunId) {
        List<Patient> records = patientRepository.findAll().stream()
                .filter(patient -> testRunId.equals(patient.getTestRunId()))
                .toList();

        ssnAllocationService.markUnused(records.stream().map(Patient::getSsn).toList());
        patientRepository.deleteByTestRunId(testRunId);
    }
}
